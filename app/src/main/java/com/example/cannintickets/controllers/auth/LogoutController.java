package com.example.cannintickets.controllers.auth;

import com.example.cannintickets.usecases.auth.logout.LogoutUserCase;

import java.util.concurrent.CompletableFuture;

public class LogoutController {
    final LogoutUserCase userInput;

    public LogoutController() {
        this.userInput = new LogoutUserCase();
    }

    public CompletableFuture<String[]> POST() {
        String[] response = userInput.execute();
        return CompletableFuture.completedFuture(response);
    }
}
