package com.example.cannintickets.boundaries.input;

import com.example.cannintickets.models.request.UserSignupRequestModel;
import com.example.cannintickets.models.response.UserSignupResponseModel;

import java.util.concurrent.CompletableFuture;

public interface UserInputBoundary {
    CompletableFuture<UserSignupResponseModel> create(UserSignupRequestModel requestModel);
}
