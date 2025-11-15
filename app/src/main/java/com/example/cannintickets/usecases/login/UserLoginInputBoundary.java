package com.example.cannintickets.usecases.login;

import com.example.cannintickets.models.auth.login.request.UserLoginRequestModel;
import com.example.cannintickets.models.auth.response.UserResponseModel;

import java.util.concurrent.CompletableFuture;

public interface UserLoginInputBoundary {
    CompletableFuture<UserResponseModel> create(UserLoginRequestModel requestModel);
}
