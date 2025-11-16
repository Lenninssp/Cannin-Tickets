package com.example.cannintickets.usecases.tickets.get;

import com.example.cannintickets.models.simple.SimpleResponseModel;

import java.util.concurrent.CompletableFuture;

public interface GetTicketInputBoundary {
    CompletableFuture<SimpleResponseModel> execute(String eventId);
}
