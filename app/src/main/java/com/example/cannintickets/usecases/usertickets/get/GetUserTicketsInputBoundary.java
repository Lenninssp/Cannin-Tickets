package com.example.cannintickets.usecases.usertickets.get;

import com.example.cannintickets.models.usertickets.UserTicketsResponseModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// If sends null then the functionality will change, that's intended
public interface GetUserTicketsInputBoundary {
    CompletableFuture<List<UserTicketsResponseModel>> execute(String eventId);
}
