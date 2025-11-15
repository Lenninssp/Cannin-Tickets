package com.example.cannintickets.models.presenters;

import com.example.cannintickets.models.response.UserSignupResponseModel;

public class UserResponseFormatter implements UserPresenter {
    @Override
    public UserSignupResponseModel prepareSuccessView(UserSignupResponseModel response) {
        return response;
    }

    @Override
    public UserSignupResponseModel prepareFailView(String error) {
        throw new Error(error);
    }
}
