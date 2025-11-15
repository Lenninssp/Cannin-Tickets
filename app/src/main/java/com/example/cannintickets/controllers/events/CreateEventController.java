package com.example.cannintickets.controllers.events;

import com.example.cannintickets.models.events.create.request.CreateEventRequestModel;
import com.example.cannintickets.models.events.create.response.CreateEventResponseModel;
import com.example.cannintickets.usecases.events.CreateEventInputBundary;
import com.example.cannintickets.usecases.events.CreateEventUseCase;

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
