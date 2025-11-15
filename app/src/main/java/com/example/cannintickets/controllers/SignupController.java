package com.example.cannintickets.controllers;

import com.example.cannintickets.boundaries.input.UserInputBoundary;
import com.example.cannintickets.models.presenters.UserResponseFormatter;
import com.example.cannintickets.models.request.UserSignupRequestModel;
import com.example.cannintickets.models.response.UserSignupResponseModel;
import com.example.cannintickets.usecases.SignupUseCase;

import java.util.concurrent.CompletableFuture;

public class SignupController {

    final UserInputBoundary userInput;

    public SignupController() {
        this.userInput = new SignupUseCase();
    }
    public CompletableFuture<UserSignupResponseModel> POST(UserSignupRequestModel requestModel){
        return userInput.create(requestModel).thenApply(success -> {
            return success;
        }).exceptionally(error -> {
            return new UserResponseFormatter().prepareFailView("Fail, the user could not be created");
        });
    }
}
