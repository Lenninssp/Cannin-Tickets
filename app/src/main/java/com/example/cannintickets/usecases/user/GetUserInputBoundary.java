package com.example.cannintickets.usecases.user;

import com.example.cannintickets.models.user.auth.UserResponseModel;

import java.util.concurrent.CompletableFuture;

public interface GetUserInputBoundary {
    CompletableFuture<UserResponseModel> execute();
}
