package com.example.cannintickets.usecases.signup;

import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.models.auth.presenters.UserPresenter;
import com.example.cannintickets.models.auth.presenters.UserResponseFormatter;
import com.example.cannintickets.models.auth.signup.request.UserSignupRequestModel;
import com.example.cannintickets.models.auth.response.UserResponseModel;
import com.example.cannintickets.repositories.UserAuthRepository;
import java.util.concurrent.CompletableFuture;

public class SignupUseCase implements UserSignupInputBoundary {
    final UserSignupFactory userSignupFactory;
    final UserPresenter userPresenter;
    final UserAuthRepository repo;

    public SignupUseCase() {
        this.userSignupFactory = new CommonUserSignupFactory();
        this.userPresenter = new UserResponseFormatter();
        this.repo = new UserAuthRepository();
    }

    @Override
    public CompletableFuture<UserResponseModel> create(UserSignupRequestModel requestModel) {
        UserSingupEntity user = userSignupFactory.create(
                requestModel.getUsername(),
                requestModel.getEmail(),
                requestModel.getPassword(),
                requestModel.getRole()
        );

        String[] userIsValid = user.isValid();
        if (userIsValid[0].equals("ERROR")) {
            UserResponseModel response = userPresenter.prepareFailView(userIsValid[1]);
            return CompletableFuture.completedFuture(response);
        }
        UserSignupRequestModel userRsModel = new UserSignupRequestModel(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
        return repo.save(userRsModel).thenApply(successMessage -> {
            UserResponseModel accountResponseModel = new UserResponseModel(user.getUsername(), user.getEmail(), user.getRole());
            // todo: here i have to then create the user table in the db
            return userPresenter.prepareSuccessView(accountResponseModel);

        }).exceptionally(error -> {
            return userPresenter.prepareFailView(error.getMessage());
        });


    }
}
