package com.example.cannintickets.usecases.orders.get;

import com.example.cannintickets.models.orders.OrderResponseModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GetOrdersInputBoundary {
    CompletableFuture<List<OrderResponseModel>> execute(String event);
}
