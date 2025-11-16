package com.example.cannintickets.models.user.auth;

public class UserResponseFormatter implements UserPresenter {
    @Override
    public UserResponseModel prepareSuccessView(UserResponseModel response) {
        return response;
    }

    @Override
    public UserResponseModel prepareFailView(String error) {
        return new UserResponseModel(error);
    }
}
