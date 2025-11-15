package com.example.cannintickets.controllers;

import com.example.cannintickets.models.auth.login.request.UserLoginRequestModel;
import com.example.cannintickets.models.auth.response.UserResponseModel;
import com.example.cannintickets.usecases.login.LoginUseCase;
import com.example.cannintickets.usecases.login.UserLoginInputBoundary;

import java.util.concurrent.CompletableFuture;

public class LoginController {
    final UserLoginInputBoundary userInput;

    public  LoginController(){ this.userInput = new LoginUseCase();
    }

    public CompletableFuture<UserResponseModel> POST(UserLoginRequestModel requestModel) {
        return userInput.execute(requestModel).thenApply(success->{
            return success;
        });
    }
}
