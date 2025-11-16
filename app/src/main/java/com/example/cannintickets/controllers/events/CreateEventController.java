package com.example.cannintickets.controllers.events;

import com.example.cannintickets.models.events.create.CreateEventRequestModel;
import com.example.cannintickets.models.events.create.CreateEventResponseModel;
import com.example.cannintickets.usecases.events.create.CreateEventInputBundary;
import com.example.cannintickets.usecases.events.create.CreateEventUseCase;

import java.util.concurrent.CompletableFuture;

public class CreateEventController {
    final CreateEventInputBundary userInput;

    public CreateEventController(){
        this.userInput = new CreateEventUseCase();
    }

    public CompletableFuture<CreateEventResponseModel> POST(CreateEventRequestModel requestModel) {
        return userInput.execute(requestModel).thenApply(result -> {
            return result;
        });
    }
}
