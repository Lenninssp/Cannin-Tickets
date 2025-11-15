package com.example.cannintickets.controllers;

import com.example.cannintickets.usecases.signup.UserInputBoundary;
import com.example.cannintickets.models.presenters.UserResponseFormatter;
import com.example.cannintickets.models.request.UserSignupRequestModel;
import com.example.cannintickets.models.response.UserSignupResponseModel;
import com.example.cannintickets.usecases.signup.SignupUseCase;

import java.util.concurrent.CompletableFuture;

public class SignupController {

    final UserInputBoundary userInput;

    public SignupController() {
        this.userInput = new SignupUseCase();
    }
    public CompletableFuture<UserSignupResponseModel> POST(UserSignupRequestModel requestModel){
        return userInput.create(requestModel).thenApply(success -> {
            return success;
        });
    }
}
