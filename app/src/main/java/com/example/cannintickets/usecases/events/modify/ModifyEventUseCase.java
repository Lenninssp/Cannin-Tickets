package com.example.cannintickets.usecases.events.modify;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventEntity;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.models.events.modify.ModifyEventRequestModel;
import com.example.cannintickets.models.events.persistence.EventPersistenceModel;
import com.example.cannintickets.models.simple.SimplePresenter;
import com.example.cannintickets.models.simple.SimpleResponseFormatter;
import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class ModifyEventUseCase implements ModifyEventInputBoundary {
    final UserAuthRepository authRepo;
    final EventRepository eventRepo;
    final UserRepository userRepo;
    final UserSignupFactory userFactory;
    final EventFactory eventFactory;
    final SimplePresenter eventPresenter;


    public ModifyEventUseCase() {
        this.authRepo = new UserAuthRepository();
        this.eventRepo = new EventRepository();
        this.userRepo = new UserRepository();
        this.userFactory = new CommonUserSignupFactory();
        this.eventFactory = new CommonEventFactory();
        this.eventPresenter = new SimpleResponseFormatter();
    }

    @Override
    public CompletableFuture<SimpleResponseModel> execute(ModifyEventRequestModel requestModel) {
        FirebaseUser user = authRepo.currentUser();
        if (user == null) {
            return CompletableFuture.completedFuture(
                    eventPresenter.prepareFailView("The user doesn't exist or isn't authenticated")
            );
        }

        return userRepo.get(user.getEmail()).thenCompose(successUser -> {
            UserSingupEntity userEntity = userFactory.create(
                    successUser.getUsername(),
                    successUser.getEmail(),
                    "",
                    successUser.getRole()
            );

            if (!userEntity.canCreateEvents()) {
                return CompletableFuture.completedFuture(
                        eventPresenter.prepareFailView("The user doesn't have enough permissions to create events")
                );
            }

            return eventRepo.get(requestModel.getId()).thenCompose(successEvent -> {

                EventEntity eventToModify = eventFactory.createFromPersistence(
                        successEvent.getId(),
                        successEvent.getName(),
                        successEvent.getDescription(),
                        LocalDateTime.parse(successEvent.getEventDate()),
                        successEvent.getLocation(),
                        successEvent.getIsPrivate(),
                        successEvent.getOrganizerId()
                );


                if (requestModel.getName() != null)
                    eventToModify.updateName(requestModel.getName());

                if (requestModel.getDescription() != null)
                    eventToModify.updateDescription(requestModel.getDescription());

                if (requestModel.getLocation() != null)
                    eventToModify.updateLocation(requestModel.getLocation());

                if (requestModel.getEventDate() != null)
                    eventToModify.updateDate(LocalDateTime.parse(requestModel.getEventDate()));

                if (requestModel.isPrivate() != null)
                    eventToModify.updatePrivacy(requestModel.isPrivate());

                if (eventToModify.isValid()[0].equals("ERROR")) {
                    return CompletableFuture.completedFuture(eventPresenter.prepareFailView("The new event is not valid"));
                }
                EventPersistenceModel updateModel = new EventPersistenceModel(
                        eventToModify.getName(),
                        eventToModify.getDescription(),
                        eventToModify.getEventDate().toString(),
                        eventToModify.getLocation(),
                        eventToModify.getIsPrivate(),
                        "",
                        user.getEmail(),
                        eventToModify.getId()
                );
                return eventRepo.modify(updateModel).thenApply(success -> {
                    return eventPresenter.prepareSuccessView("The update was successful");
                }).exceptionally(error -> {
                    return eventPresenter.prepareFailView(error.getMessage());
                });
            });
        }).exceptionally(error -> {return eventPresenter.prepareFailView(error.getMessage());});
    }


}
