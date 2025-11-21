package com.example.cannintickets.usecases.usertickets.modify;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventEntity;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.entities.usertickets.CommonUserTicketFactory;
import com.example.cannintickets.entities.usertickets.UserTicketFactory;
import com.example.cannintickets.models.simple.SimplePresenter;
import com.example.cannintickets.models.simple.SimpleResponseFormatter;
import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.example.cannintickets.repositories.UserTicketRepository;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class ModifyUserTicketUseCase implements ModifyUserTicketInputBoundary {
    final UserAuthRepository authRepo;
    final UserRepository userRepo;
    final EventRepository eventRepo;
    final UserTicketFactory ticketFactory;
    final UserTicketRepository ticketRepo;
    final UserSignupFactory userFactory;
    final EventFactory eventFactory;
    final SimplePresenter presenter;

    public ModifyUserTicketUseCase() {
        this.ticketRepo = new UserTicketRepository();
        this.authRepo = new UserAuthRepository();
        this.userRepo = new UserRepository();
        this.eventRepo = new EventRepository();
        this.ticketFactory = new CommonUserTicketFactory();
        this.userFactory = new CommonUserSignupFactory();
        this.eventFactory = new CommonEventFactory();
        this.presenter = new SimpleResponseFormatter();
    }

    @Override
    public CompletableFuture<SimpleResponseModel> execute(String userTicketId, Boolean newChecked) {
        FirebaseUser user = authRepo.currentUser();

        if (user == null) {
            return CompletableFuture.completedFuture(
                    presenter.prepareFailView("The user is not authenticated or doesnt exist")
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
                        presenter.prepareFailView("The user doesn't have enough permissions to create events")
                );
            }

            return ticketRepo.get(userTicketId).thenCompose(userTicket -> {
                return eventRepo.get(userTicket.getEventId()).thenCompose(event -> {
                    EventEntity eventEntity = eventFactory.createFromPersistence(
                            event.getId(),
                            event.getName(),
                            event.getDescription(),
                            LocalDateTime.parse(event.getEventDate()),
                            event.getLocation(),
                            event.getIsPrivate(),
                            event.getOrganizerId()
                    );
                    if (!eventEntity.canModify(user.getEmail())) {
                        return CompletableFuture.completedFuture(
                                presenter.prepareFailView("The user is not the creator of the event")
                        );
                    }

                    if(userTicket.getChecked() == false) {

                        return ticketRepo.modify(userTicketId, true).thenApply(success -> {
                            return presenter.prepareSuccessView("Success, user ticket was checked");
                        }).exceptionally(error -> {
                            return presenter.prepareFailView("Error checking userTicket: " + error);
                        });
                    }
                    else {
                        return ticketRepo.modify(userTicketId, false).thenApply(success -> {
                            return presenter.prepareSuccessView("Success, user ticket was unchecked");
                        }).exceptionally(error -> {
                            return presenter.prepareFailView("Error unchecking userTicket: " + error);
                        });
                    }
                }).exceptionally(error -> {
                    return presenter.prepareFailView("The ticket event doesn't exist: " + error);
                });
            }).exceptionally(error -> {
                return presenter.prepareFailView("The ticket doesnt exist: " + error.getMessage());
            });


        }).exceptionally(error -> {
            return presenter.prepareFailView("Failed: " + error.getMessage());
        });
    }
}
