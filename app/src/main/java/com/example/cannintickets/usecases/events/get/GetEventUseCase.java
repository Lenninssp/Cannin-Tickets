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
import com.example.cannintickets.repositories.ImageRepository;
import com.example.cannintickets.repositories.SaveRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class GetEventUseCase implements GetEventInputBoundary {
    final EventRepository eventRepo;
    final UserAuthRepository authRepo;
    final EventFactory eventFactory;
    final GetEventPresenter eventPresenter;
    final UserRepository userRepo;
    final UserSignupFactory userFactory;
    final ImageRepository imageRepo;
    final SaveRepository saveRepo;

    public GetEventUseCase() {
        this.eventFactory = new CommonEventFactory();
        this.authRepo = new UserAuthRepository();
        this.eventRepo = new EventRepository();
        this.eventPresenter = new GetEventResponseFormatter();
        this.userRepo = new UserRepository();
        this.userFactory = new CommonUserSignupFactory();
        this.imageRepo = new ImageRepository();
        this.saveRepo = new SaveRepository();
    }
    @Override
    public CompletableFuture<List<GetEventResponseModel>> execute(Boolean saved) {
        FirebaseUser user = authRepo.currentUser();

        if (user == null) {
            return CompletableFuture.completedFuture(
                    eventPresenter.prepareFailView(
                            "The user is not authenticated or doesnt exist"
                    )
            );
        }

        return userRepo.get(user.getEmail())
                .thenCompose(successUser -> {
                    UserSingupEntity userEntity = userFactory.create(
                            successUser.getUsername(),
                            successUser.getEmail(),
                            "",
                            successUser.getRole()
                    );

                    if (userEntity.canCreateEvents()) {
                        return eventRepo.getFromEmail(user.getEmail())
                                .thenCompose(events -> {
                                    List<CompletableFuture<GetEventResponseModel>> eventFutures = events.stream()
                                            .map(event -> processEventWithImage(event, true))
                                            .collect(Collectors.toList());

                                    return CompletableFuture.allOf(eventFutures.toArray(new CompletableFuture[0]))
                                            .thenApply(v -> {
                                                List<GetEventResponseModel> results = eventFutures.stream()
                                                        .map(CompletableFuture::join)
                                                        .filter(Objects::nonNull)
                                                        .collect(Collectors.toList());

                                                return eventPresenter.prepareSuccessView(results);
                                            })
                                            .exceptionally(error -> {
                                                return eventPresenter.prepareFailView(
                                                        "There was an error processing events: " + error.getMessage()
                                                );
                                            });
                                });
                    } else {
                        if (Boolean.TRUE.equals(saved)) {
                            return saveRepo.getFromUser(user.getEmail())
                                    .thenCompose(savedIds ->
                                            eventRepo.getPublic().thenCompose(events -> {
                                                List<EventPersistenceModel> filtered = events.stream()
                                                        .filter(e -> savedIds.contains(e.getId()))
                                                        .collect(Collectors.toList());

                                                List<CompletableFuture<GetEventResponseModel>> futures =
                                                        filtered.stream()
                                                                .map(event -> processEventWithImage(event, false))
                                                                .collect(Collectors.toList());

                                                return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                                                        .thenApply(v ->
                                                                futures.stream()
                                                                        .map(CompletableFuture::join)
                                                                        .filter(Objects::nonNull)
                                                                        .collect(Collectors.toList())
                                                        )
                                                        .thenApply(eventPresenter::prepareSuccessView);
                                            })
                                    )
                                    .exceptionally(e ->
                                            eventPresenter.prepareFailView("Error while filtering saved events")
                                    );
                        }

                        return eventRepo.getPublic()
                                .thenCompose(events -> {
                                    List<CompletableFuture<GetEventResponseModel>> eventFutures = events.stream()
                                            .map(event -> processEventWithImage(event, false))
                                            .collect(Collectors.toList());

                                    return CompletableFuture.allOf(eventFutures.toArray(new CompletableFuture[0]))
                                            .thenApply(v -> {
                                                List<GetEventResponseModel> results = eventFutures.stream()
                                                        .map(CompletableFuture::join)
                                                        .filter(Objects::nonNull)
                                                        .collect(Collectors.toList());

                                                return eventPresenter.prepareSuccessView(results);
                                            })
                                            .exceptionally(error -> {
                                                return eventPresenter.prepareFailView(
                                                        "There was an error processing events: " + error.getMessage()
                                                );
                                            });
                                });
                    }
                })
                .exceptionally(error -> {
                    return eventPresenter.prepareFailView(
                            "Error: the user maybe doesn't exist " + error.getMessage()
                    );
                });
    }

    private CompletableFuture<GetEventResponseModel> processEventWithImage(
            EventPersistenceModel event,
            boolean includePrivate) {

        LocalDateTime eventDate = LocalDateTime.parse(event.getEventDate());
        EventEntity eventEntity = eventFactory.create(
                event.getName(),
                event.getDescription(),
                eventDate,
                event.getLocation(),
                null,
                null
        );

        if (!eventEntity.isValid()[0].equals("SUCCESS")) {
            return CompletableFuture.completedFuture(null);
        }

        if (event.getOrganizerImageUrl() != null && !event.getOrganizerImageUrl().isBlank()) {
            return imageRepo.get(event.getOrganizerImageUrl())
                    .thenApply(image -> createResponseModel(event, image, includePrivate))
                    .exceptionally(error -> {
                        return createResponseModel(event, new File(""), includePrivate);
                    });
        } else {
            return CompletableFuture.completedFuture(
                    createResponseModel(event, new File(""), includePrivate)
            );
        }
    }

    private GetEventResponseModel createResponseModel(
            EventPersistenceModel event,
            File image,
            boolean includePrivate) {

        if (includePrivate) {
            return new GetEventResponseModel(
                    event.getId(),
                    event.getName(),
                    event.getDescription(),
                    event.getEventDate(),
                    event.getLocation(),
                    image,
                    event.getOrganizerId(),
                    event.getCreationDate(),
                    event.getIsPrivate()
            );
        } else {
            return new GetEventResponseModel(
                    event.getId(),
                    event.getName(),
                    event.getDescription(),
                    event.getEventDate(),
                    event.getLocation(),
                    image,
                    event.getOrganizerId()
            );
        }
    }
}