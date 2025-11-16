package com.example.cannintickets.usecases.tickets.get;

import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.models.tickets.get.GetTicketResponseModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GetTicketInputBoundary {
    CompletableFuture<List<GetTicketResponseModel>> execute(String eventId);
}
