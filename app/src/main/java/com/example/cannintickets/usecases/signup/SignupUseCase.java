package com.example.cannintickets.usecases.signup;

import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.models.presenters.UserPresenter;
import com.example.cannintickets.models.presenters.UserResponseFormatter;
import com.example.cannintickets.models.request.UserSignupRequestModel;
import com.example.cannintickets.models.response.UserSignupResponseModel;
import com.example.cannintickets.repositories.UserAuthRepository;
import java.util.concurrent.CompletableFuture;

public class SignupUseCase implements UserInputBoundary {
    final UserSignupFactory userSignupFactory;
    final UserPresenter userPresenter;

    public SignupUseCase() {
        this.userSignupFactory = new CommonUserSignupFactory();
        this.userPresenter = new UserResponseFormatter();
    }

    @Override
    public CompletableFuture<UserSignupResponseModel> create(UserSignupRequestModel requestModel) {
        // todo: check if user doesn't exist
        UserSingupEntity user = userSignupFactory.create(
                requestModel.getUsername(),
                requestModel.getEmail(),
                requestModel.getPassword(),
                requestModel.getRole()
        );

        String[] userIsValid = user.isValid();
        if (userIsValid[0].equals("ERROR")) {
            UserSignupResponseModel response = userPresenter.prepareFailView(userIsValid[1]);
            return CompletableFuture.completedFuture(response);
        }

        UserSignupRequestModel userRsModel = new UserSignupRequestModel(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );

        UserAuthRepository repo = new UserAuthRepository();
        return repo.save(userRsModel).thenApply(successMessage -> {
            UserSignupResponseModel accountResponseModel = new UserSignupResponseModel(user.getUsername(), user.getEmail(), user.getRole());
            return userPresenter.prepareSuccessView(accountResponseModel);

        }).exceptionally(error -> {
            return userPresenter.prepareFailView(error.getMessage());
        });


    }
}
