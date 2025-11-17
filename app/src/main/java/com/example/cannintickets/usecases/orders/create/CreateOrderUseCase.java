package com.example.cannintickets.usecases.orders.create;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.ticket.CommonTicketFactory;
import com.example.cannintickets.entities.ticket.TicketFactory;
import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.models.simple.SimplePresenter;
import com.example.cannintickets.models.simple.SimpleResponseFormatter;
import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.TicketRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;

import java.util.concurrent.CompletableFuture;

public class CreateOrderUseCase implements CreateOderInputBoundary {
    final TicketRepository ticketRepo;
    final EventRepository eventRepo;
    final UserAuthRepository authRepo;
    final UserRepository userRepo;
    final SimplePresenter presenter;
    final EventFactory eventFactory;
    final UserSignupFactory userFactory;
    final TicketFactory ticketFactory;

    public CreateOrderUseCase() {
        this.ticketRepo = new TicketRepository();
        this.eventRepo = new EventRepository();
        this.authRepo = new UserAuthRepository();
        this.userRepo = new UserRepository();
        this.presenter = new SimpleResponseFormatter();
        this.eventFactory = new CommonEventFactory();
        this.userFactory = new CommonUserSignupFactory();
        this.ticketFactory = new CommonTicketFactory();
    }

    public CompletableFuture<SimpleResponseModel> execute() {
        return CompletableFuture.completedFuture(presenter.prepareSuccessView(""));
    }
}