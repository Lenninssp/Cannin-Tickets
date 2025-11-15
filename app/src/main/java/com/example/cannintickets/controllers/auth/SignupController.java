package com.example.cannintickets.controllers.auth;

import com.example.cannintickets.usecases.auth.signup.UserSignupInputBoundary;
import com.example.cannintickets.models.auth.signup.request.UserSignupRequestModel;
import com.example.cannintickets.models.auth.response.UserResponseModel;
import com.example.cannintickets.usecases.auth.signup.SignupUseCase;

import java.util.concurrent.CompletableFuture;

public class SignupController {

    final UserSignupInputBoundary userInput;

    public SignupController() {
        this.userInput = new SignupUseCase();
    }
    public CompletableFuture<UserResponseModel> POST(UserSignupRequestModel requestModel){
        return userInput.create(requestModel).thenApply(success -> {
            return success;
        });
    }
}
