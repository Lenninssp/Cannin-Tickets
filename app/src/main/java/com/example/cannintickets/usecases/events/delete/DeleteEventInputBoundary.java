package com.example.cannintickets.usecases.events.delete;

import com.example.cannintickets.models.simple.SimpleResponseModel;

import java.util.concurrent.CompletableFuture;

public interface DeleteEventInputBoundary {
    CompletableFuture<SimpleResponseModel> execute(String id);
}
