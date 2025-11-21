package com.example.cannintickets.usecases.saves;

import com.example.cannintickets.entities.event.EventEntity;
import com.example.cannintickets.entities.order.OrderEntity;
import com.example.cannintickets.entities.ticket.TicketEntity;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.models.orders.OrderPersistenceModel;
import com.example.cannintickets.models.saves.SavePresenter;
import com.example.cannintickets.models.saves.SaveResponseFormatter;
import com.example.cannintickets.models.saves.SaveResponseModel;
import com.example.cannintickets.models.usertickets.UserTicketsPersistence;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.SaveRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ToggleSaveUseCase implements ToggleSaveInputBoundary {
    private final UserAuthRepository authRepo;
    private final UserRepository userRepo;
    private final EventRepository eventRepo;
    private final SaveRepository saveRepo;
    private final SavePresenter presenter;
    public ToggleSaveUseCase() {
        this.authRepo = new UserAuthRepository();
        this.userRepo = new UserRepository();
        this.eventRepo = new EventRepository();
        this.saveRepo = new SaveRepository();
        this.presenter = new SaveResponseFormatter();
    }

    public CompletableFuture<SaveResponseModel> execute(String userEmail, String eventId) {
        FirebaseUser firebaseUser = authRepo.currentUser();
        if (firebaseUser == null) {
            return CompletableFuture.completedFuture(
                    presenter.prepareFailView("The user doesn't exist or isn't authenticated")
            );
        }

        return userRepo.get(firebaseUser.getEmail())
                .thenCompose(userPersistence ->
                        eventRepo.get(eventId)
                                .thenCompose(eventPersistence ->
                                        saveRepo.getFromUserAndEventId(userEmail, eventId)
                                                .thenCompose(existingSave -> {
                                                    if (existingSave != null) {
                                                        return saveRepo.delete(userEmail, eventId)
                                                                .thenApply(result -> presenter.prepareSuccessView(false));
                                                    } else {
                                                        return saveRepo.create(userEmail, eventId)
                                                                .thenApply(result -> presenter.prepareSuccessView(true));
                                                    }
                                                })
                                                .exceptionally(error ->
                                                        presenter.prepareFailView("Error connecting to db")
                                                )
                                )
                )
                .exceptionally(error ->
                        presenter.prepareFailView("Unexpected error: " + error.getMessage())
                );
    }
}
