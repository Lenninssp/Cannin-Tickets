package com.example.cannintickets.usecases.events.delete;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.models.events.delete.DeleteEventPresenter;
import com.example.cannintickets.models.events.delete.DeleteEventResponseFormatter;
import com.example.cannintickets.models.events.delete.DeleteEventResponseModel;
import com.example.cannintickets.models.events.get.GetEventResponseModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DeleteEventUseCase implements DeleteEventInputBoundary {
    final UserAuthRepository authRepo;
    final EventRepository eventRepo;
    final UserRepository userRepo;
    final UserSignupFactory userFactory;
    final EventFactory eventFactory;
    final DeleteEventPresenter eventPresenter;

    public DeleteEventUseCase() {
        this.authRepo = new UserAuthRepository();
        this.eventRepo = new EventRepository();
        this.userRepo = new UserRepository();
        this.userFactory = new CommonUserSignupFactory();
        this.eventFactory = new CommonEventFactory();
        this.eventPresenter = new DeleteEventResponseFormatter();
    }

    public CompletableFuture<DeleteEventResponseModel> execute(String id) {

        List<GetEventResponseModel> returnList = new ArrayList<>();
        FirebaseUser user = authRepo.currentUser();
        if (user == null) {
            return CompletableFuture.completedFuture(
                    eventPresenter.prepareFailView(
                            new DeleteEventResponseModel(
                                    "The user doesn't exist or isn't authenticated",
                                    false
                            )
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

            if (!userEntity.canCreateEvents()) {
                return CompletableFuture.completedFuture(
                        eventPresenter.prepareFailView(
                                new DeleteEventResponseModel(
                                        "The user doesn't have enough permissions to create events",
                                        false
                                )

                        )
                );
            }

            return eventRepo.delete(id).thenApply(success -> {
                return eventPresenter.prepareSuccessView(
                        new DeleteEventResponseModel(
                                "The event was deleted successfully",
                                true
                        )
                );
            }).exceptionally(error -> {
                return eventPresenter.prepareFailView(
                        new DeleteEventResponseModel(
                                error.getMessage(),
                                false
                        )
                );
            });

        }).exceptionally(error -> {
                    return eventPresenter.prepareFailView(
                            new DeleteEventResponseModel(
                                    error.getMessage(),
                                    false
                            )
                    );
                }
        );
    }
}
