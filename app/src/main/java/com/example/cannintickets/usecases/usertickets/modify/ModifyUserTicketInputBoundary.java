package com.example.cannintickets.usecases.usertickets.modify;

import com.example.cannintickets.models.simple.SimpleResponseModel;

import java.util.concurrent.CompletableFuture;

public interface ModifyUserTicketInputBoundary {
    CompletableFuture<SimpleResponseModel> execute(String userTicketId, Boolean newChecked);
}
