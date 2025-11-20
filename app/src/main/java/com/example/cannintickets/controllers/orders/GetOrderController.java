package com.example.cannintickets.controllers.orders;

import com.example.cannintickets.models.orders.OrderPersistenceModel;
import com.example.cannintickets.models.orders.OrderResponseModel;
import com.example.cannintickets.usecases.orders.get.GetOrdersUserCase;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetOrderController {
    final GetOrdersUserCase userInput;

    public GetOrderController(){
        this.userInput = new GetOrdersUserCase();
    }

    public CompletableFuture<List<OrderResponseModel>> GET(String eventId) {
        return userInput.execute(eventId).thenApply(result -> {
            return result;
        });
    }
}
