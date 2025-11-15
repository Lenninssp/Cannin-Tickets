package com.example.cannintickets.usecases.login;

import com.example.cannintickets.entities.user.login.CommonUserLoginFactory;
import com.example.cannintickets.entities.user.login.UserLoginEntity;
import com.example.cannintickets.entities.user.login.UserLoginFactory;
import com.example.cannintickets.models.auth.login.request.UserLoginRequestModel;
import com.example.cannintickets.models.auth.presenters.UserPresenter;
import com.example.cannintickets.models.auth.presenters.UserResponseFormatter;
import com.example.cannintickets.models.auth.response.UserResponseModel;
import com.example.cannintickets.models.auth.signup.request.UserSignupRequestModel;
import com.example.cannintickets.repositories.UserAuthRepository;

import java.util.concurrent.CompletableFuture;

public class LoginUseCase implements UserLoginInputBoundary{
    final UserAuthRepository repo;
    final UserPresenter userPresenter;
    final UserLoginFactory userFactory;

    public LoginUseCase(){
        this.repo = new UserAuthRepository();
        this.userPresenter = new UserResponseFormatter();
        this.userFactory = new CommonUserLoginFactory();
    }

    @Override
    public CompletableFuture<UserResponseModel> execute(UserLoginRequestModel requestModel) {
        UserLoginEntity user = userFactory.create(
                requestModel.getEmail(),
                requestModel.getPassword()
        );

        String[] userIsValid = user.isValid();

        if(userIsValid[0].equals("ERROR")) {
            UserResponseModel response = userPresenter.prepareFailView(userIsValid[1]);
            return CompletableFuture.completedFuture(response);
        }

        UserLoginRequestModel userRsModel = new UserLoginRequestModel(
                user.getEmail(),
                user.getPassword()
        );

        return repo.login(userRsModel).thenApply(successMessage -> {
            // todo: fetch too the rest of the user information
            UserResponseModel accountResponseModel = new UserResponseModel("", user.getEmail(), "");
            return userPresenter.prepareSuccessView(accountResponseModel);
        }).exceptionally( errorMessage -> {
            return userPresenter.prepareFailView(errorMessage.getMessage());
        });
    }
}
