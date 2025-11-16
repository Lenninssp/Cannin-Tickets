package com.example.cannintickets.controllers.events;

import com.example.cannintickets.models.events.create.CreateEventRequestModel;
import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.usecases.events.create.CreateEventInputBoundary;
import com.example.cannintickets.usecases.events.create.CreateEventUseCase;

import java.util.concurrent.CompletableFuture;

public class CreateEventController {
    final CreateEventInputBoundary userInput;

    public CreateEventController(){
        this.userInput = new CreateEventUseCase();
    }

    public CompletableFuture<SimpleResponseModel> POST(CreateEventRequestModel requestModel) {
        return userInput.execute(requestModel).thenApply(result -> {
            return result;
        });
    }
}
