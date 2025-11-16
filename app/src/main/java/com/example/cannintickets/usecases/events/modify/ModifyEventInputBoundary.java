package com.example.cannintickets.usecases.events.modify;

import com.example.cannintickets.models.events.modify.ModifyEventRequestModel;
import com.example.cannintickets.models.simple.SimpleResponseModel;

import java.util.concurrent.CompletableFuture;

public interface ModifyEventInputBoundary {
    CompletableFuture<SimpleResponseModel> execute(ModifyEventRequestModel requestModel);
}
