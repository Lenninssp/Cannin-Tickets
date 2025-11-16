package com.example.cannintickets.usecases.auth.login;

import com.example.cannintickets.models.user.auth.UserLoginRequestModel;
import com.example.cannintickets.models.user.auth.UserResponseModel;

import java.util.concurrent.CompletableFuture;

public interface UserLoginInputBoundary {
    CompletableFuture<UserResponseModel> execute(UserLoginRequestModel requestModel);
}
