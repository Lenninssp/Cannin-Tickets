package com.example.cannintickets.controllers.events;

import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.usecases.events.delete.DeleteEventInputBoundary;
import com.example.cannintickets.usecases.events.delete.DeleteEventUseCase;

import java.util.concurrent.CompletableFuture;

public class DeleteEventController {
    final DeleteEventInputBoundary userInput;

    public DeleteEventController(){
        this.userInput = new DeleteEventUseCase();
    }

    public CompletableFuture<SimpleResponseModel> DELETE(String id) {
        return userInput.execute(id).thenApply(result -> {
            return result;
        });
    }
}
