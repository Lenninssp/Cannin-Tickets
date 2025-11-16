package com.example.cannintickets.usecases.tickets.modify;

import com.example.cannintickets.models.simple.SimpleResponseModel;
import com.example.cannintickets.models.tickets.modify.ModifyTicketRequestModel;

import java.util.concurrent.CompletableFuture;

public interface ModifyTicketInputBoundary {
    CompletableFuture<SimpleResponseModel> execute(ModifyTicketRequestModel id);
}
