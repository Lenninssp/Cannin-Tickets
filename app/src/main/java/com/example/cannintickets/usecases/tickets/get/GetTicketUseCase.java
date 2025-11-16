package com.example.cannintickets.usecases.tickets.get;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventEntity;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.ticket.CommonTicketFactory;
import com.example.cannintickets.entities.ticket.TicketEntity;
import com.example.cannintickets.entities.ticket.TicketFactory;
import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.models.tickets.get.GetTicketPresenter;
import com.example.cannintickets.models.tickets.get.GetTicketResponseFormatter;
import com.example.cannintickets.models.tickets.get.GetTicketResponseModel;
import com.example.cannintickets.models.tickets.persistence.TicketPersistenceModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.TicketRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetTicketUseCase implements GetTicketInputBoundary{
    final TicketRepository ticketRepo;
    final UserAuthRepository authRepo;
    final UserRepository userRepo;
    final EventRepository eventRepo;
    final TicketFactory ticketFactory;
    final UserSignupFactory userFactory;
    final EventFactory eventFactory;
    final GetTicketPresenter presenter;

    public GetTicketUseCase(){
        this.ticketRepo = new TicketRepository();
        this.authRepo = new UserAuthRepository();
        this.userRepo = new UserRepository();
        this.ticketFactory = new CommonTicketFactory();
        this.presenter = new GetTicketResponseFormatter();
        this.userFactory = new CommonUserSignupFactory();
        this.eventRepo = new EventRepository();
        this.eventFactory = new CommonEventFactory();
    }

    @Override
    public CompletableFuture<List<GetTicketResponseModel>> execute(String eventId) {
        List<GetTicketResponseModel> returnList = new ArrayList<>();
        FirebaseUser user = authRepo.currentUser();
        if (user == null) {
            return CompletableFuture.completedFuture(
                    presenter.prepareFailView(
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


            return eventRepo.get(eventId).thenCompose(succesEvent -> {
                EventEntity eventEntity = eventFactory.create(
                        succesEvent.getName(),
                        succesEvent.getDescription(),
                        LocalDateTime.parse(succesEvent.getEventDate()),
                        succesEvent.getLocation(),
                        succesEvent.getIsPrivate(),
                        succesEvent.getOrganizerId()
                );

                if (eventEntity.isValid()[0].equals("ERROR")) {
                    return CompletableFuture.completedFuture(
                            presenter.prepareFailView("The event you want to access is not valid")
                    );
                }

                return ticketRepo.getFromEvent(eventId).thenApply(success -> {
                    for(TicketPersistenceModel ticket : success) {
                        TicketEntity ticketEntity = ticketFactory.createFromPersistence(
                                ticket.getId(),
                                ticket.getName(),
                                ticket.getEventId(),
                                ticket.getCapacity(),
                                ticket.getPrice(),
                                ticket.getSold()
                        );
                        if (ticketEntity.isValid()[0].equals("SUCCESS")){
                            returnList.add(new GetTicketResponseModel(
                                    ticket.getId(),
                                    ticket.getName(),
                                    ticket.getPrice(),
                                    ticket.getEventId()
                            ));
                        }
                    }
                    return presenter.prepareSuccessView(returnList);
                });
            }).exceptionally(error -> {
                return presenter.prepareFailView("There was an error getting the tickets: " + error.getMessage());
            });

        }).exceptionally(error -> {
            return presenter.prepareFailView("Error: the user maybe doesn't exist " + error.getMessage());
        });

        }
}
