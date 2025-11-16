package com.example.cannintickets.models.simple;

public interface SimplePresenter {
    SimpleResponseModel prepareSuccessView(String message);
    SimpleResponseModel prepareFailView(String error);
}