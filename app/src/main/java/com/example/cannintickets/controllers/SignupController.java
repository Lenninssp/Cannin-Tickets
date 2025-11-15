package com.example.cannintickets.controllers;

import com.example.cannintickets.boundaries.input.UserInputBoundary;
import com.example.cannintickets.models.request.UserSignupRequestModel;
import com.example.cannintickets.models.response.UserSignupResponseModel;
import com.example.cannintickets.usecases.SignupUseCase;

public class SignupController {

    final UserInputBoundary userInput;

    public SignupController() {
        this.userInput = new SignupUseCase();
    }
    public UserSignupResponseModel POST(UserSignupRequestModel requestModel){
        System.out.println("Here at least");
        return userInput.create(requestModel);
    }
}
