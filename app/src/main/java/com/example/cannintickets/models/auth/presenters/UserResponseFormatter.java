package com.example.cannintickets.models.auth.presenters;

import com.example.cannintickets.models.auth.response.UserResponseModel;

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
