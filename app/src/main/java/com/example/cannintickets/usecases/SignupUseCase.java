package com.example.cannintickets.usecases;

import com.example.cannintickets.boundaries.input.UserInputBoundary;
import com.example.cannintickets.entities.user.CommonUserFactory;
import com.example.cannintickets.entities.user.UserEntity;
import com.example.cannintickets.entities.user.UserFactory;
import com.example.cannintickets.models.presenters.UserPresenter;
import com.example.cannintickets.models.presenters.UserResponseFormatter;
import com.example.cannintickets.models.request.UserSignupRequestModel;
import com.example.cannintickets.models.response.UserSignupResponseModel;
import com.example.cannintickets.repositories.UserRepository;
import java.util.concurrent.CompletableFuture;

public class SignupUseCase implements UserInputBoundary {
    final UserFactory userFactory;
    final UserPresenter userPresenter;

    public SignupUseCase() {
        this.userFactory = new CommonUserFactory();
        this.userPresenter = new UserResponseFormatter();
    }

    @Override
    public CompletableFuture<UserSignupResponseModel> create(UserSignupRequestModel requestModel) {
        // todo: check if user doesn't exist
        UserEntity user = userFactory.create(
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

        UserRepository repo = new UserRepository();
        return repo.save(userRsModel).thenApply(successMessage -> {
            UserSignupResponseModel accountResponseModel = new UserSignupResponseModel(user.getUsername(), user.getEmail(), user.getRole());
            return userPresenter.prepareSuccessView(accountResponseModel);

        }).exceptionally(error -> {
            return userPresenter.prepareFailView(error.getMessage());
        });


    }
}
