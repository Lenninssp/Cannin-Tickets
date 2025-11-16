package com.example.cannintickets.controllers.tickets;

import com.example.cannintickets.controllers.events.ModifyEventController;
import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.models.tickets.modify.ModifyTicketRequestModel;
import com.example.cannintickets.usecases.tickets.modify.ModifyTicketInputBoundary;
import com.example.cannintickets.usecases.tickets.modify.ModifyTicketUseCase;

import java.util.concurrent.CompletableFuture;

public class ModifyTicketController {
    final ModifyTicketInputBoundary userInput;

    public ModifyTicketController() {
        this.userInput = new ModifyTicketUseCase();
    }

    public CompletableFuture<SimpleResponseModel> POST(ModifyTicketRequestModel requestModel) {
        return  userInput.execute(requestModel).thenApply(result -> {
            return result;
        });
    }


}
