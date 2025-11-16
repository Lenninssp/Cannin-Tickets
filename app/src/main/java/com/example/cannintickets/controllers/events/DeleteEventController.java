package com.example.cannintickets.controllers.events;

import com.example.cannintickets.models.events.delete.DeleteEventResponseModel;
import com.example.cannintickets.usecases.events.delete.DeleteEventInputBoundary;
import com.example.cannintickets.usecases.events.delete.DeleteEventUseCase;

import java.util.concurrent.CompletableFuture;

public class DeleteEventController {
    final DeleteEventInputBoundary userInput;

    public DeleteEventController(){
        this.userInput = new DeleteEventUseCase();
    }

    public CompletableFuture<DeleteEventResponseModel> DELETE(String id) {
        return userInput.execute(id).thenApply(result -> {
            return result;
        });
    }
}
