package com.example.cannintickets.usecases.events.create;

import com.example.cannintickets.models.events.create.CreateEventRequestModel;
import com.example.cannintickets.models.simple.SimpleResponseModel;

import java.util.concurrent.CompletableFuture;

public interface CreateEventInputBoundary {
    CompletableFuture<SimpleResponseModel> execute(CreateEventRequestModel requestModel);
}
