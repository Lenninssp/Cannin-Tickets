package com.example.cannintickets.controllers.events;

import com.example.cannintickets.models.events.get.GetEventResponseModel;
import com.example.cannintickets.usecases.events.get.GetEventInputBoundary;
import com.example.cannintickets.usecases.events.get.GetEventUseCase;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetEventsController {
    final GetEventInputBoundary userInput;

    public GetEventsController(){
        this.userInput = new GetEventUseCase();
    }

    public CompletableFuture<List<GetEventResponseModel>> GET() {
        return userInput.execute().thenApply(result -> {
            return result;
        });
    }
}
