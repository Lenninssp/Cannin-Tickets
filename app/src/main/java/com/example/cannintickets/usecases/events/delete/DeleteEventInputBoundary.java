package com.example.cannintickets.usecases.events.delete;

import com.example.cannintickets.models.events.delete.DeleteEventResponseModel;

import java.util.concurrent.CompletableFuture;

public interface DeleteEventInputBoundary {
    CompletableFuture<DeleteEventResponseModel> execute(String id);
}
