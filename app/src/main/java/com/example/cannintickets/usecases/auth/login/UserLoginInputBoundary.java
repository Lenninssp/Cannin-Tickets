package com.example.cannintickets.usecases.auth.login;

import com.example.cannintickets.models.user.auth.request.UserLoginRequestModel;
import com.example.cannintickets.models.user.auth.response.UserResponseModel;

import java.util.concurrent.CompletableFuture;

public interface UserLoginInputBoundary {
    CompletableFuture<UserResponseModel> execute(UserLoginRequestModel requestModel);
}
