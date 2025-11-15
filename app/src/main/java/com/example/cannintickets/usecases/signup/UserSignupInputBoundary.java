package com.example.cannintickets.usecases.signup;

import com.example.cannintickets.models.auth.signup.request.UserSignupRequestModel;
import com.example.cannintickets.models.auth.response.UserResponseModel;

import java.util.concurrent.CompletableFuture;

public interface UserSignupInputBoundary {
    CompletableFuture<UserResponseModel> create(UserSignupRequestModel requestModel);
}
