package com.example.cannintickets.controllers.usertickets;

import com.example.cannintickets.models.usertickets.UserTicketsResponseModel;
import com.example.cannintickets.usecases.usertickets.get.GetUserTicketsInputBoundary;
import com.example.cannintickets.usecases.usertickets.get.GetUserTicketsUseCase;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetUserTicketsController {
    final GetUserTicketsInputBoundary userInput;

    public GetUserTicketsController() {
        this.userInput = new GetUserTicketsUseCase();
    }

    public CompletableFuture<List<UserTicketsResponseModel>> GET(String eventId) {
        return userInput.execute(eventId).thenApply(result -> {
            return result;
        });
    }

    public CompletableFuture<List<UserTicketsResponseModel>> GETbuyer() {
        return userInput.execute(null).thenApply(result -> {
            return result;
        });
    }
}
