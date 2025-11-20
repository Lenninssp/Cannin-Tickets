package com.example.cannintickets.usecases.orders.create;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventEntity;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.order.CommonOrderFactory;
import com.example.cannintickets.entities.order.OrderEntity;
import com.example.cannintickets.entities.order.OrderFactory;
import com.example.cannintickets.entities.ticket.CommonTicketFactory;
import com.example.cannintickets.entities.ticket.TicketEntity;
import com.example.cannintickets.entities.ticket.TicketFactory;
import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.entities.usertickets.CommonUserTicketFactory;
import com.example.cannintickets.entities.usertickets.UserTicketEntity;
import com.example.cannintickets.entities.usertickets.UserTicketFactory;
import com.example.cannintickets.models.orders.OrderPersistenceModel;
import com.example.cannintickets.models.orders.OrderRequestModel;
import com.example.cannintickets.models.simple.SimplePresenter;
import com.example.cannintickets.models.simple.SimpleResponseFormatter;
import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.models.usertickets.UserTicketsPersistence;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.OrdersRepotitory;
import com.example.cannintickets.repositories.TicketRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.example.cannintickets.repositories.UserTicketRepository;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class CreateOrderUseCase implements CreateOderInputBoundary {

    private final TicketRepository ticketRepo;
    private final EventRepository eventRepo;
    private final UserAuthRepository authRepo;
    private final UserRepository userRepo;
    private final SimplePresenter presenter;
    private final EventFactory eventFactory;
    private final UserSignupFactory userFactory;
    private final TicketFactory ticketFactory;
    private final OrderFactory orderFactory;
    private final OrdersRepotitory ordersRepotitory;
    private final UserTicketFactory userTicketFactory;
    private final UserTicketRepository userTicketRepository;

    public CreateOrderUseCase() {
        this.ticketRepo = new TicketRepository();
        this.eventRepo = new EventRepository();
        this.authRepo = new UserAuthRepository();
        this.userRepo = new UserRepository();
        this.presenter = new SimpleResponseFormatter();
        this.eventFactory = new CommonEventFactory();
        this.userFactory = new CommonUserSignupFactory();
        this.ticketFactory = new CommonTicketFactory();
        this.orderFactory = new CommonOrderFactory();
        this.ordersRepotitory = new OrdersRepotitory();
        this.userTicketFactory = new CommonUserTicketFactory();
        this.userTicketRepository = new UserTicketRepository();
    }

    @Override
    public CompletableFuture<SimpleResponseModel> execute(OrderRequestModel orderRequest) {

        FirebaseUser firebaseUser = authRepo.currentUser();
        if (firebaseUser == null) {
            return CompletableFuture.completedFuture(
                    presenter.prepareFailView("The user doesn't exist or isn't authenticated")
            );
        }

        return userRepo.get(firebaseUser.getEmail())
                .thenCompose(userPersistence -> {

                    UserSingupEntity userEntity = userFactory.create(
                            userPersistence.getUsername(),
                            userPersistence.getEmail(),
                            "",
                            userPersistence.getRole()
                    );

                    if (!userEntity.canCreateEvents()) {
                        return CompletableFuture.completedFuture(
                                presenter.prepareFailView("Error: sellers can't create orders")
                        );
                    }

                    return eventRepo.get(orderRequest.getEventId())
                            .thenCompose(eventPersistence -> {

                                EventEntity eventEntity = eventFactory.create(
                                        eventPersistence.getName(),
                                        eventPersistence.getDescription(),
                                        LocalDateTime.parse(eventPersistence.getEventDate()),
                                        eventPersistence.getLocation(),
                                        eventPersistence.getIsPrivate(),
                                        eventPersistence.getOrganizerId()
                                );

                                return ticketRepo.get(orderRequest.getTicketId())
                                        .thenCompose(ticketPersistence -> {

                                            TicketEntity ticketEntity = ticketFactory.createFromPersistence(
                                                    ticketPersistence.getId(),
                                                    ticketPersistence.getName(),
                                                    ticketPersistence.getEventId(),
                                                    ticketPersistence.getCapacity(),
                                                    ticketPersistence.getPrice(),
                                                    ticketPersistence.getSold()
                                            );

                                            if (!ticketEntity.getEventId().equals(eventEntity.getId())) {
                                                return CompletableFuture.completedFuture(
                                                        presenter.prepareFailView("The ticket does not belong to the event")
                                                );
                                            }

                                            Double total = orderRequest.getQuantity() * ticketEntity.getPrice();

                                            OrderEntity orderEntity = orderFactory.create(
                                                    LocalDateTime.now(),
                                                    LocalDateTime.now(),
                                                    total,
                                                    firebaseUser.getEmail(),
                                                    orderRequest.getEventId(),
                                                    eventPersistence.getName(),
                                                    orderRequest.getPaymentIntentId(),
                                                    orderRequest.getQuantity(),
                                                    orderRequest.getTicketId(),
                                                    ticketPersistence.getName(),
                                                    ticketPersistence.getPrice()
                                            );

                                            OrderPersistenceModel persistenceOrder = new OrderPersistenceModel(
                                                    orderEntity.getCustomerEmail(),
                                                    orderEntity.getTicketId(),
                                                    orderEntity.getTicketName(),
                                                    orderEntity.getTicketPrice(),
                                                    orderEntity.getQuantity(),
                                                    orderEntity.getTotal(),
                                                    orderEntity.getPaymentIntentId(),
                                                    orderEntity.getEventName(),
                                                    orderEntity.getEventId(),
                                                    userPersistence.getCreatedAt(),
                                                    userPersistence.getUpdatedAt()
                                            );

                                            return ordersRepotitory.create(persistenceOrder)
                                                    .thenCompose(result -> {

                                                        int quantity = orderRequest.getQuantity();

                                                        CompletableFuture<Void> ticketsFuture = CompletableFuture.completedFuture(null);

                                                        for (int i = 0; i < quantity; i++) {
                                                            UserTicketsPersistence utPersistence = new UserTicketsPersistence(
                                                                    false,
                                                                    eventPersistence.getEventDate(),
                                                                    orderRequest.getEventId(),
                                                                    eventEntity.getName(),
                                                                    eventEntity.getLocation(),
                                                                    orderRequest.getTicketId(),
                                                                    ticketPersistence.getName(),
                                                                    userPersistence.getEmail()
                                                            );

                                                            ticketsFuture = ticketsFuture.thenCompose(v ->
                                                                    userTicketRepository.create(utPersistence)
                                                                            .thenApply(ignore -> null)
                                                            );
                                                        }

                                                        return ticketsFuture
                                                                .thenApply(v -> presenter.prepareSuccessView("The order and tickets were created successfully"))
                                                                .exceptionally(error -> presenter.prepareFailView("Error: " + error.getMessage()));
                                                    })
                                                    .exceptionally(error ->
                                                            presenter.prepareFailView("Error creating order: " + error.getMessage())
                                                    );
                                        });
                            });
                })
                .exceptionally(error ->
                        presenter.prepareFailView("Unexpected error: " + error.getMessage())
                );
    }
}
