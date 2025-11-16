package com.example.cannintickets.controllers.tickets;

import com.example.cannintickets.models.tickets.get.GetTicketResponseModel;
import com.example.cannintickets.usecases.tickets.get.GetTicketInputBoundary;
import com.example.cannintickets.usecases.tickets.get.GetTicketUseCase;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetTicketsController {
    final GetTicketInputBoundary userInput;

    public GetTicketsController() {
        this.userInput = new GetTicketUseCase();
    }

    public CompletableFuture<List<GetTicketResponseModel>> GET(String eventId) {
        return userInput.execute(eventId).thenApply(result -> {
            return result;
        });
    }
}
