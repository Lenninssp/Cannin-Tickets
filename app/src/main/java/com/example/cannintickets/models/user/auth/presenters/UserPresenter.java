package com.example.cannintickets.models.user.auth.presenters;

import com.example.cannintickets.models.user.auth.response.UserResponseModel;

public interface UserPresenter {
    UserResponseModel prepareSuccessView(UserResponseModel user);
    UserResponseModel prepareFailView(String error);
}
