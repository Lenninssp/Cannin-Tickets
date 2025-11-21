package com.example.cannintickets.controllers.auth;

import com.example.cannintickets.models.user.auth.UserResponseModel;
import com.example.cannintickets.usecases.user.GetUserInputBoundary;
import com.example.cannintickets.usecases.user.GetUserUseCase;

import java.util.concurrent.CompletableFuture;

public class GetUserController {
    final GetUserInputBoundary userInput;

    public GetUserController() {
        this.userInput = new GetUserUseCase();
    }

    public CompletableFuture<UserResponseModel> POST() {
        return userInput.execute().thenApply(result -> {
            return result;
        });
    }
}
