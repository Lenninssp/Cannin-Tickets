package com.example.cannintickets.controllers.tickets;

import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.usecases.tickets.delete.DeleteTicketInputBoundary;
import com.example.cannintickets.usecases.tickets.delete.DeleteTicketUseCase;

import java.util.concurrent.CompletableFuture;

public class DeleteTicketController {
    final DeleteTicketInputBoundary userInput;

    public DeleteTicketController() {
        this.userInput = new DeleteTicketUseCase();
    }

    public CompletableFuture<SimpleResponseModel> DELETE(String id) {
        return userInput.execute(id).thenApply(result -> {
            return result;
        });
    }
}
