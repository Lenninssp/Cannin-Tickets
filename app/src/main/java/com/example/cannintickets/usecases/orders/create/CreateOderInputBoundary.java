package com.example.cannintickets.usecases.orders.create;

import com.example.cannintickets.models.orders.OrderRequestModel;
import com.example.cannintickets.models.simple.SimpleResponseModel;

import java.util.concurrent.CompletableFuture;

public interface CreateOderInputBoundary {
    CompletableFuture<SimpleResponseModel> execute(OrderRequestModel orderRequest);
}
