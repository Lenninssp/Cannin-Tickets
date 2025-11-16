package com.example.cannintickets.models.events.modify;

public class ModifyEventResponseModel {
    String message;
    boolean successful;

    public ModifyEventResponseModel(String message, boolean successful) {
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
