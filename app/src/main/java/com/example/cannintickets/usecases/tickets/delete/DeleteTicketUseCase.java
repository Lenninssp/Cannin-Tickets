package com.example.cannintickets.usecases.tickets.delete;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.ticket.CommonTicketFactory;
import com.example.cannintickets.entities.ticket.TicketEntity;
import com.example.cannintickets.entities.ticket.TicketFactory;
import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.models.simple.SimplePresenter;
import com.example.cannintickets.models.simple.SimpleResponseFormatter;
import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.TicketRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class DeleteTicketUseCase implements DeleteTicketInputBoundary {
    final UserAuthRepository authRepo;
    final UserRepository userRepo;
    final EventRepository eventRepo;
    final TicketRepository ticketRepo;
    final EventFactory eventFactory;
    final UserSignupFactory userFactory;
    final TicketFactory ticketFactory;
    final SimplePresenter presenter;

    public DeleteTicketUseCase() {
        this.authRepo = new UserAuthRepository();
        this.userRepo = new UserRepository();
        this.eventRepo = new EventRepository();
        this.ticketRepo = new TicketRepository();
        this.eventFactory = new CommonEventFactory();
        this.userFactory = new CommonUserSignupFactory();
        this.presenter = new SimpleResponseFormatter();
        this.ticketFactory = new CommonTicketFactory();
    }


    @Override
    public CompletableFuture<SimpleResponseModel> execute(String id) {
        FirebaseUser user = authRepo.currentUser();
        if (user == null) {
            return CompletableFuture.completedFuture(
                    presenter.prepareFailView("The user doesn't exist or isn't authenticated")
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
                        presenter.prepareFailView("The user doesn't have enough permissions to create events"
                        )
                );
            }

            return ticketRepo.get(id).thenCompose(ticketPersistence -> {
                if (ticketPersistence == null) {
                    return CompletableFuture.completedFuture(
                            presenter.prepareFailView("The ticket does not exist")
                    );
                }
                return eventRepo.get(ticketPersistence.getEventId()).thenCompose(eventPersistence -> {

                    if (eventPersistence == null) {
                        return CompletableFuture.completedFuture(
                                presenter.prepareFailView("The event tied to this ticket does not exist")
                        );
                    }

                    var eventEntity = eventFactory.createFromPersistence(
                            eventPersistence.getId(),
                            eventPersistence.getName(),
                            eventPersistence.getDescription(),
                            LocalDateTime.parse(eventPersistence.getEventDate()),
                            eventPersistence.getLocation(),
                            eventPersistence.getIsPrivate(),
                            eventPersistence.getOrganizerId()
                    );

                    if (!eventEntity.canModify(successUser.getEmail())) {
                        return CompletableFuture.completedFuture(
                                presenter.prepareFailView("You do not have permission to delete this ticket")
                        );
                    }

                    return ticketRepo.delete(id).thenApply(msg ->
                            presenter.prepareSuccessView("Ticket deleted successfully")
                    ).exceptionally(error ->
                            presenter.prepareFailView(error.getMessage())
                    );
                }).exceptionally(error -> {
                    return presenter.prepareFailView(error.getMessage());
                });
            });

        }).exceptionally(error -> {
            return presenter.prepareFailView(error.getMessage());
        });
    }
}
