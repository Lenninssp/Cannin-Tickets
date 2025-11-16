package com.example.cannintickets.controllers.tickets;

import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.models.tickets.create.CreateTicketRequestModel;
import com.example.cannintickets.usecases.tickets.create.CreateTicketInputBoundary;
import com.example.cannintickets.usecases.tickets.create.CreateTicketUseCase;

import java.util.concurrent.CompletableFuture;

public class CreateTicketController {
    final CreateTicketInputBoundary userInput;

    public CreateTicketController(){
        this.userInput = new CreateTicketUseCase();
    }

    public CompletableFuture<SimpleResponseModel> POST(CreateTicketRequestModel requestModel) {
        return userInput.execute(requestModel).thenApply(result -> {
            return result;
        });
    }
}
