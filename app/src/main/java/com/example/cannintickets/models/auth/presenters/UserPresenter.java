package com.example.cannintickets.models.auth.presenters;

import com.example.cannintickets.models.auth.response.UserResponseModel;

public interface UserPresenter {
    UserResponseModel prepareSuccessView(UserResponseModel user);
    UserResponseModel prepareFailView(String error);
}
