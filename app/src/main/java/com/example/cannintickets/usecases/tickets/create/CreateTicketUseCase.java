package com.example.cannintickets.usecases.tickets.create;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventEntity;
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
import com.example.cannintickets.models.tickets.create.CreateTicketRequestModel;
import com.example.cannintickets.models.tickets.persistence.TicketPersistenceModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.TicketRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.example.cannintickets.services.EventValidator;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class CreateTicketUseCase implements CreateTicketInputBoundary{
    final TicketRepository ticketRepo;
    final EventRepository eventRepo;
    final UserAuthRepository authRepo;
    final UserRepository userRepo;
    final SimplePresenter ticketPresenter;
    final EventFactory eventFactory;
    final UserSignupFactory userFactory;
    final TicketFactory ticketFactory;

    public CreateTicketUseCase() {
        this.ticketRepo = new TicketRepository();
        this.eventRepo = new EventRepository();
        this.authRepo = new UserAuthRepository();
        this.userRepo = new UserRepository();
        this.ticketPresenter = new SimpleResponseFormatter();
        this.eventFactory = new CommonEventFactory();
        this.userFactory = new CommonUserSignupFactory();
        this.ticketFactory = new CommonTicketFactory();
    }

    public CompletableFuture<SimpleResponseModel> execute(CreateTicketRequestModel requestModel) {
        FirebaseUser user = authRepo.currentUser();
        if (user == null) {
            return CompletableFuture.completedFuture(
                    ticketPresenter.prepareFailView("The user doesn't exist or isn't authenticated")
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
                        ticketPresenter.prepareFailView("The user doesn't have enough permissions to create events" )
                );
            }
            return eventRepo.get(requestModel.getEventId()).thenCompose(succesEvent -> {
                EventEntity eventEntity = eventFactory.create(
                        succesEvent.getName(),
                        succesEvent.getDescription(),
                        LocalDateTime.parse(succesEvent.getEventDate()),
                        succesEvent.getLocation(),
                        succesEvent.getIsPrivate(),
                        succesEvent.getOrganizerId()
                );

                if (!eventEntity.canModify(user.getEmail())) {
                    return CompletableFuture.completedFuture(
                            ticketPresenter.prepareFailView("You do not have permissions to create tickets for this event")
                    );
                }

                TicketEntity ticketEntity = ticketFactory.create(
                        requestModel.getName(),
                        requestModel.getEventId(),
                        requestModel.getCapacity(),
                        requestModel.getPrice(),
                        0
                );

                if (ticketEntity.isValid()[0].equals("ERROR")){
                    return CompletableFuture.completedFuture(
                            ticketPresenter.prepareFailView(ticketEntity.isValid()[1])
                    );
                }
                TicketPersistenceModel ticketPersisted = new TicketPersistenceModel(
                        requestModel.getName(),
                        requestModel.getEventId(),
                        requestModel.getCapacity(),
                        0,
                        requestModel.getPrice()
                );

                return ticketRepo.create(ticketPersisted).thenApply(successMessage -> {
                    return ticketPresenter.prepareSuccessView("The ticket was successfully created");
                }).exceptionally(errorMessage -> {
                    return ticketPresenter.prepareFailView("There was an error creating the ticket " + errorMessage);
                });
            }).exceptionally(error -> {
                return  ticketPresenter.prepareFailView("The event could not be found in the db");
            });

        }).exceptionally(error -> {
            return  ticketPresenter.prepareFailView("The user could not be found in the db");
        });
    }

}
