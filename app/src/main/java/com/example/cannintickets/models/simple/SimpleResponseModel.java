package com.example.cannintickets.models.simple;

public class SimpleResponseModel {
    private final boolean success;
    private final String message;

    public SimpleResponseModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
