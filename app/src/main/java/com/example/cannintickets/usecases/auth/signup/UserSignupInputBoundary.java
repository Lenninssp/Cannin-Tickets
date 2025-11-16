package com.example.cannintickets.usecases.auth.signup;

import com.example.cannintickets.models.user.auth.UserSignupRequestModel;
import com.example.cannintickets.models.user.auth.UserResponseModel;

import java.util.concurrent.CompletableFuture;

public interface UserSignupInputBoundary {
    CompletableFuture<UserResponseModel> create(UserSignupRequestModel requestModel);
}
