package com.example.cannintickets.models.user.auth;

public interface UserPresenter {
    UserResponseModel prepareSuccessView(UserResponseModel user);
    UserResponseModel prepareFailView(String error);
}
