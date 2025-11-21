package com.example.cannintickets.usecases.saves;

import com.example.cannintickets.models.saves.SaveResponseModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ToggleSaveInputBoundary {
    CompletableFuture<SaveResponseModel> execute(String userEmail, String eventId);
}
