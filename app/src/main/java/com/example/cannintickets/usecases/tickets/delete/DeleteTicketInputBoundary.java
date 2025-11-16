package com.example.cannintickets.usecases.tickets.delete;

import com.example.cannintickets.models.simple.SimpleResponseModel;

import java.util.concurrent.CompletableFuture;

public interface DeleteTicketInputBoundary {
    CompletableFuture<SimpleResponseModel> execute(String id);
}
