package com.example.cannintickets.usecases.events.create;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventEntity;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.image.CommonImageFactory;
import com.example.cannintickets.entities.image.ImageEntity;
import com.example.cannintickets.entities.image.ImageFactory;
import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.models.events.persistence.EventPersistenceModel;
import com.example.cannintickets.models.events.create.CreateEventPresenter;
import com.example.cannintickets.models.events.create.CreateEventResponseFormatter;
import com.example.cannintickets.models.events.create.CreateEventRequestModel;
import com.example.cannintickets.models.events.create.CreateEventResponseModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.ImageRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class CreateEventUseCase implements CreateEventInputBundary {
    final EventRepository repo;
    final UserAuthRepository authRepo;
    final ImageRepository imageRepo;
    final UserRepository userRepo;
    final CreateEventPresenter eventPresenter;
    final EventFactory eventFactory;
    final ImageFactory imageFactory;
    final UserSignupFactory userFactory;

    public CreateEventUseCase() {
        this.repo = new EventRepository();
        this.eventPresenter = new CreateEventResponseFormatter();
        this.authRepo = new UserAuthRepository();
        this.userRepo = new UserRepository();
        this.eventFactory = new CommonEventFactory();
        this.imageRepo = new ImageRepository();
        this.imageFactory = new CommonImageFactory();
        this.userFactory = new CommonUserSignupFactory();
    }

    public CompletableFuture<CreateEventResponseModel> execute(CreateEventRequestModel requestModel) {
        FirebaseUser user = authRepo.currentUser();
        if (user == null) {
            return CompletableFuture.completedFuture(
                    eventPresenter.prepareFailView(new CreateEventResponseModel(
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
                                new CreateEventResponseModel(
                                        "The user doesn't have enough permissions to create events",
                                        false
                                )

                        )
                );
            }
            //todo: here I should check the user table to check the permissions
            ImageEntity image = imageFactory.create(
                    requestModel.getCoverImage()
            );
            // todo: make a better image upload implementation because this one aint it

            //taken from: https://stackoverflow.com/questions/41992970/how-do-i-convert-an-iso-8601-date-time-string-to-java-time-localdatetime
            LocalDateTime eventDate = LocalDateTime.parse(requestModel.getEventDate());

            EventEntity event = eventFactory.create(
                    requestModel.getName(),
                    requestModel.getDescription(),
                    eventDate,
                    requestModel.getLocation(),
                    null,
                    null
            );


            if (event.isValid()[0].equals("ERROR")) {
                return CompletableFuture.completedFuture(
                        eventPresenter.prepareFailView(
                                new CreateEventResponseModel(
                                        event.isValid()[1],
                                        false
                                )

                        )
                );
            }

            EventPersistenceModel eventPersisted = new EventPersistenceModel(
                    requestModel.getName(),
                    requestModel.getDescription(),
                    requestModel.getEventDate(),
                    requestModel.getLocation(),
                    requestModel.isPrivate(),
                    "",
                    user.getEmail(),
                    ""
            );

            return repo.create(eventPersisted).thenApply(successMesssage -> {
                return eventPresenter.prepareSuccessView(new CreateEventResponseModel(
                        "The event was created successfully",
                        true
                ));
            }).exceptionally(errorMessage -> {
                return eventPresenter.prepareSuccessView(new CreateEventResponseModel(
                        errorMessage.getMessage(),
                        false
                ));
            });
        }).exceptionally(error -> {
            return  eventPresenter.prepareFailView(
                    new CreateEventResponseModel(
                            "The user could not be found in the db",
                            false
                    ));
        });


    }
}
