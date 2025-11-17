package com.example.cannintickets.usecases.events.get;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventEntity;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.models.events.get.GetEventPresenter;
import com.example.cannintickets.models.events.get.GetEventResponseFormatter;
import com.example.cannintickets.models.events.get.GetEventResponseModel;
import com.example.cannintickets.models.events.persistence.EventPersistenceModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class GetEventUseCase implements GetEventInputBoundary {
    final EventRepository eventRepo;
    final UserAuthRepository authRepo;
    final EventFactory eventFactory;
    final GetEventPresenter eventPresenter;
    final UserRepository userRepo;
    final UserSignupFactory userFactory;


    public GetEventUseCase() {
        this.eventFactory = new CommonEventFactory();
        this.authRepo = new UserAuthRepository();
        this.eventRepo = new EventRepository();
        this.eventPresenter = new GetEventResponseFormatter();
        this.userRepo = new UserRepository();
        this.userFactory = new CommonUserSignupFactory();
    }

    @Override
    public CompletableFuture<List<GetEventResponseModel>> execute() {
        List<GetEventResponseModel> returnList = new ArrayList<>();
        FirebaseUser user = authRepo.currentUser();
        if (user == null) {
            return CompletableFuture.completedFuture(
                    eventPresenter.prepareFailView(
                            "The user is not authenticated or doesnt exist"
                    )
            );
        }

        return userRepo.get(user.getEmail()).thenCompose(successUser -> {
            UserSingupEntity userEntity = userFactory.create(
                    successUser.getUsername(),
                    successUser.getEmail(),
                    "",
                    successUser.getRole()
            );
            if (userEntity.canCreateEvents()) {
                return eventRepo.getFromEmail(user.getEmail()).thenApply(successMessage -> {
                    for (EventPersistenceModel event : successMessage) {
                        LocalDateTime eventDate = LocalDateTime.parse(event.getEventDate());
                        EventEntity eventEntity = eventFactory.create(
                                event.getName(),
                                event.getDescription(),
                                eventDate,
                                event.getLocation(),
                                null,
                                null
                        );

                        // todo: return event image here
                        if (eventEntity.isValid()[0].equals("SUCCESS")) {


                            returnList.add(new GetEventResponseModel(
                                    event.getId(),
                                    event.getName(),
                                    event.getDescription(),
                                    event.getEventDate(),
                                    event.getLocation(),
                                    new File(""),
                                    event.getOrganizerId(),
                                    event.getCreationDate(),
                                    event.getIsPrivate()
                            ));
                        }
                    }
                return eventPresenter.prepareSuccessView(returnList);
            }).exceptionally(error -> {
                return eventPresenter.prepareFailView(
                        "There was an error: " + error.getMessage()
                );
            });
        }

            return eventRepo.getPublic().thenApply(successMessage -> {

                for (EventPersistenceModel event : successMessage) {
                    LocalDateTime eventDate = LocalDateTime.parse(event.getEventDate());
                    EventEntity eventEntity = eventFactory.create(
                            event.getName(),
                            event.getDescription(),
                            eventDate,
                            event.getLocation(),
                            null,
                            null
                    );

                    // todo: return event image here
                    if (eventEntity.isValid()[0].equals("SUCCESS")) {

                        returnList.add(new GetEventResponseModel(
                                event.getId(),
                                event.getName(),
                                event.getDescription(),
                                event.getEventDate(),
                                event.getLocation(),
                                new File(""),
                                event.getOrganizerId()));

                    }
                }
                return eventPresenter.prepareSuccessView(returnList);
            }).exceptionally(error -> {
                return eventPresenter.prepareFailView(
                        "There was an error: " + error.getMessage()
                );

            });
        }).exceptionally(error -> {
            return eventPresenter.prepareFailView("Error: the user maybe doesn't exist " + error.getMessage());
        });
    }
}
