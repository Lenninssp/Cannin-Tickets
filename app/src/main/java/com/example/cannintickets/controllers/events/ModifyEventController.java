package com.example.cannintickets.controllers.events;

import com.example.cannintickets.models.events.modify.ModifyEventRequestModel;
import com.example.cannintickets.models.events.modify.ModifyEventResponseModel;
import com.example.cannintickets.usecases.events.modify.ModifyEventInputBoundary;
import com.example.cannintickets.usecases.events.modify.ModifyEventUseCase;

import java.util.concurrent.CompletableFuture;

public class ModifyEventController {
    final ModifyEventInputBoundary userInput;

    public ModifyEventController() {
        this.userInput = new ModifyEventUseCase();
    }

    public CompletableFuture<ModifyEventResponseModel> POST(ModifyEventRequestModel requestModel){
        return userInput.execute(requestModel).thenApply(result -> {
            return result;
        });
    }
}
