package com.example.cannintickets.models.events.create.response;

public class CreateEventResponseModel {
    String message;
    boolean successful;

    public CreateEventResponseModel(String message, boolean successful) {
        this.message = message;
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
