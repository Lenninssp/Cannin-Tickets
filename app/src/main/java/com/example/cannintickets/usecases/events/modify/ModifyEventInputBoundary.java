package com.example.cannintickets.usecases.events.modify;

import com.example.cannintickets.models.events.modify.ModifyEventRequestModel;
import com.example.cannintickets.models.events.modify.ModifyEventResponseModel;

import java.util.concurrent.CompletableFuture;

public interface ModifyEventInputBoundary {
    CompletableFuture<ModifyEventResponseModel> execute(ModifyEventRequestModel requestModel);
}
