package com.example.cannintickets.usecases.auth.signup;

import com.example.cannintickets.models.user.auth.request.UserSignupRequestModel;
import com.example.cannintickets.models.user.auth.response.UserResponseModel;

import java.util.concurrent.CompletableFuture;

public interface UserSignupInputBoundary {
    CompletableFuture<UserResponseModel> create(UserSignupRequestModel requestModel);
}
