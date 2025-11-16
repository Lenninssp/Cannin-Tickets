package com.example.cannintickets.models.simple;

public class SimpleResponseFormatter implements SimplePresenter {

    @Override
    public SimpleResponseModel prepareSuccessView(String message) {
        return new SimpleResponseModel(true, message);
    }

    @Override
    public SimpleResponseModel prepareFailView(String error) {
        return new SimpleResponseModel(false, error);
    }
}
