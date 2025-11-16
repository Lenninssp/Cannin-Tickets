package com.example.cannintickets.models.events.delete;

public class DeleteEventResponseModel {
    String message;
    boolean successful;

    public DeleteEventResponseModel(String message, boolean successful) {
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
