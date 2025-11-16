package com.example.cannintickets.usecases.events.get;

import com.example.cannintickets.models.events.get.response.GetEventResponseModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GetEventInputBoundary {
    CompletableFuture<List<GetEventResponseModel>> execute();
}
