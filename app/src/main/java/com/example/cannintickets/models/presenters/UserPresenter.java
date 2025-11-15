package com.example.cannintickets.models.presenters;

import com.example.cannintickets.models.response.UserSignupResponseModel;

public interface UserPresenter {
    UserSignupResponseModel prepareSuccessView(UserSignupResponseModel user);
    UserSignupResponseModel prepareFailView(String error);
}
