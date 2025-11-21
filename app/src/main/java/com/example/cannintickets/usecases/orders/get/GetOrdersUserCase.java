package com.example.cannintickets.usecases.orders.get;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventEntity;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.entities.usertickets.CommonUserTicketFactory;
import com.example.cannintickets.entities.usertickets.UserTicketFactory;
import com.example.cannintickets.models.orders.OrderPersistenceModel;
import com.example.cannintickets.models.orders.OrderPresenter;
import com.example.cannintickets.models.orders.OrderResponseFormatter;
import com.example.cannintickets.models.orders.OrderResponseModel;
import com.example.cannintickets.models.usertickets.UserTicketPresenter;
import com.example.cannintickets.models.usertickets.UserTicketResponseFormatter;
import com.example.cannintickets.models.usertickets.UserTicketsPersistence;
import com.example.cannintickets.models.usertickets.UserTicketsResponseModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.OrdersRepotitory;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.example.cannintickets.repositories.UserTicketRepository;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetOrdersUserCase implements GetOrdersInputBoundary {
    final UserAuthRepository authRepo;
    final UserRepository userRepo;
    final EventRepository eventRepo;
    final UserTicketFactory ticketFactory;
    final UserTicketRepository ticketRepo;
    final UserSignupFactory userFactory;
    final EventFactory eventFactory;
    final OrderPresenter presenter;
    final OrdersRepotitory orderRepo;

    public GetOrdersUserCase() {
        this.ticketRepo = new UserTicketRepository();
        this.authRepo = new UserAuthRepository();
        this.userRepo = new UserRepository();
        this.eventRepo = new EventRepository();
        this.ticketFactory = new CommonUserTicketFactory();
        this.userFactory = new CommonUserSignupFactory();
        this.eventFactory = new CommonEventFactory();
        this.presenter = new OrderResponseFormatter();
        this.orderRepo = new OrdersRepotitory();
    }

    @Override
    public CompletableFuture<List<OrderResponseModel>> execute(String eventId) {
        List<OrderResponseModel> returnList = new ArrayList<>();
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

            if (userEntity.canCreateEvents()) {

                if (eventId != null) {
                    return eventRepo.get(eventId).thenCompose(event -> {
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
                                    presenter.prepareFailView("You not the owner of this event")
                            );
                        }

                        return orderRepo.getFromEvent(eventEntity.getId()).thenApply(orders -> {
                            for (OrderPersistenceModel order : orders) {
                                returnList.add(new OrderResponseModel(
                                        LocalDateTime.parse(order.getCreatedAt()),
                                        order.getEventName(),
                                        order.getQuantity(),
                                        order.getTicketName(),
                                        order.getTicketPrice(),
                                        order.getTotal(),
                                        order.getCustomerEmail()
                                ));
                            }
                            return presenter.prepareSuccessView(returnList);
                        });
                    });
                } else {
                    return CompletableFuture.completedFuture(
                            presenter.prepareFailView("error reading the eventId, be sure you included it in the parameters")
                    );
                }
            }

            return orderRepo.getFromCustomer(user.getEmail()).thenApply(orders -> {
                for (OrderPersistenceModel order : orders) {
                    returnList.add(new OrderResponseModel(
                            LocalDateTime.parse(order.getCreatedAt()),
                            order.getEventName(),
                            order.getQuantity(),
                            order.getTicketName(),
                            order.getTicketPrice(),
                            order.getTotal(),
                            order.getCustomerEmail()
                    ));
                }
                return presenter.prepareSuccessView(returnList);
            });
        }).exceptionally(error -> presenter.prepareFailView(error.getMessage()));
    }
}
