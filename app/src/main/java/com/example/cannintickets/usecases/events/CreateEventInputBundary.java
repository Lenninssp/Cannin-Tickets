package com.example.cannintickets.usecases.events;

import com.example.cannintickets.models.events.create.request.CreateEventRequestModel;
import com.example.cannintickets.models.events.create.response.CreateEventResponseModel;

import java.util.concurrent.CompletableFuture;

public interface CreateEventInputBundary {
    CompletableFuture<CreateEventResponseModel> execute(CreateEventRequestModel requestModel);
}
