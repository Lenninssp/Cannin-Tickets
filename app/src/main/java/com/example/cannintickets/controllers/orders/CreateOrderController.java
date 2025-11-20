package com.example.cannintickets.controllers.orders;

import com.example.cannintickets.models.orders.OrderRequestModel;
import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.usecases.orders.create.CreateOrderUseCase;

import java.util.concurrent.CompletableFuture;

public class CreateOrderController {
    final CreateOrderUseCase userInput;

    public CreateOrderController() {
        this.userInput = new CreateOrderUseCase();
    }

    public CompletableFuture<SimpleResponseModel> POST(OrderRequestModel requestModel) {
        return userInput.execute(requestModel).thenApply(result -> {
            return result;
        });
    }
}
