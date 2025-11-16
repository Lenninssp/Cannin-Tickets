package com.example.cannintickets.usecases.events.modify;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventEntity;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.models.events.modify.ModifyEventPresenter;

import com.example.cannintickets.models.events.modify.ModifyEventReponseFormatter;
import com.example.cannintickets.models.events.modify.ModifyEventRequestModel;
import com.example.cannintickets.models.events.modify.ModifyEventResponseModel;
import com.example.cannintickets.models.events.persistence.EventPersistenceModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class ModifyEventUseCase implements  ModifyEventInputBoundary{
    final UserAuthRepository authRepo;
    final EventRepository eventRepo;
    final UserRepository userRepo;
    final UserSignupFactory userFactory;
    final EventFactory eventFactory;
    final ModifyEventPresenter eventPresenter;


    public ModifyEventUseCase() {
        this.authRepo = new UserAuthRepository();
        this.eventRepo = new EventRepository();
        this.userRepo = new UserRepository();
        this.userFactory = new CommonUserSignupFactory();
        this.eventFactory = new CommonEventFactory();
        this.eventPresenter = new ModifyEventReponseFormatter();
    }

    @Override
    public CompletableFuture<ModifyEventResponseModel> execute(ModifyEventRequestModel requestModel) {
        FirebaseUser user = authRepo.currentUser();
        if (user == null) {
            return CompletableFuture.completedFuture(
                    eventPresenter.prepareFailView(new ModifyEventResponseModel(
                            "The user doesn't exist or isn't authenticated",
                            false
                    ))
            );
        }

        return userRepo.get(user.getEmail()).thenCompose(successUser -> {
            UserSingupEntity userEntity = userFactory.create(
                    successUser.getUsername(),
                    successUser.getEmail(),
                    "",
                    successUser.getRole()
            );

            if (!userEntity.canCreateEvents()){
                return CompletableFuture.completedFuture(
                        eventPresenter.prepareFailView(
                                new ModifyEventResponseModel(
                                        "The user doesn't have enough permissions to create events",
                                        false
                                )

                        )
                );
            }

            return eventRepo.get(requestModel.getId()).thenCompose(successEvent -> {
                System.out.println("REQUEST MODEL ID 0 = " + successEvent.getId());

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

                if (eventToModify.isValid()[0].equals("ERROR")){
                    return CompletableFuture.completedFuture(
                            new ModifyEventResponseModel(
                                    "The new event is not valid",
                                    false
                            )
                    );
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
                    return eventPresenter.prepareSuccessView(
                            new ModifyEventResponseModel(
                                    "The update was successful",
                                    true
                            )
                    );
                }).exceptionally(error -> {
                    return eventPresenter.prepareFailView(
                            new ModifyEventResponseModel(
                                    error.getMessage(),
                                    false
                            )
                    );
                });
            });
        }).exceptionally(error -> {
            return eventPresenter.prepareFailView(
                    new ModifyEventResponseModel(
                            error.getMessage(),
                            false
                    )
            );
        });
    }


}
