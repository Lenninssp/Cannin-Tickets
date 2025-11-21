package com.example.cannintickets.controllers.usertickets;

import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.usecases.usertickets.modify.ModifyUserTicketInputBoundary;
import com.example.cannintickets.usecases.usertickets.modify.ModifyUserTicketUseCase;

import java.util.concurrent.CompletableFuture;

public class ModifyUserTicketController {
    final ModifyUserTicketInputBoundary userInput;

    public ModifyUserTicketController() {
        this.userInput = new ModifyUserTicketUseCase();
    }

    public CompletableFuture<SimpleResponseModel> POST(String userTicketId, Boolean newChecked){
        return userInput.execute(userTicketId, newChecked).thenApply(result -> {
            return result;
        });
    }

}