package com.example.cannintickets.controllers.save;

import com.example.cannintickets.models.saves.SaveResponseModel;
import com.example.cannintickets.usecases.saves.ToggleSaveUseCase;

import java.util.concurrent.CompletableFuture;

public class ToggleEventSave {
    final ToggleSaveUseCase userInput;

    public ToggleEventSave () {
        this.userInput = new ToggleSaveUseCase();
    }

    public CompletableFuture<SaveResponseModel> POST(String userEmail, String eventId) {
        return userInput.execute(userEmail, eventId).thenApply(result -> {
            return result;
        });
    }
}
