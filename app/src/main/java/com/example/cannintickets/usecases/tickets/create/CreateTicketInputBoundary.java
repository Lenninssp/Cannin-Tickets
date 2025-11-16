package com.example.cannintickets.usecases.tickets.create;

import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.models.tickets.create.CreateTicketRequestModel;

import java.util.concurrent.CompletableFuture;

public interface CreateTicketInputBoundary {
    CompletableFuture<SimpleResponseModel> execute(CreateTicketRequestModel requestModel);
}
