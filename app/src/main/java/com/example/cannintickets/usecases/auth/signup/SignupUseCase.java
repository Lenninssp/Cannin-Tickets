package com.example.cannintickets.usecases.auth.signup;

import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.models.user.auth.presenters.UserPresenter;
import com.example.cannintickets.models.user.auth.presenters.UserResponseFormatter;
import com.example.cannintickets.models.user.auth.request.UserSignupRequestModel;
import com.example.cannintickets.models.user.auth.response.UserResponseModel;
import com.example.cannintickets.models.user.persistence.UserPersistenceModel;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;

import java.util.concurrent.CompletableFuture;

public class SignupUseCase implements UserSignupInputBoundary {
    final UserSignupFactory userSignupFactory;
    final UserPresenter userPresenter;
    final UserAuthRepository repo;
    final UserRepository userRepo;

    public SignupUseCase() {
        this.userSignupFactory = new CommonUserSignupFactory();
        this.userPresenter = new UserResponseFormatter();
        this.repo = new UserAuthRepository();
        this.userRepo = new UserRepository();
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
            UserPersistenceModel persistenceUser = new UserPersistenceModel(
                    user.getEmail(),
                    user.getUsername(),
                    user.getRole()
            );
            UserResponseModel accountResponseModel = new UserResponseModel(user.getUsername(), user.getEmail(), user.getRole());
            userRepo.create(persistenceUser).thenApply(successMessage2 -> {
                return userPresenter.prepareSuccessView(accountResponseModel);
            }).exceptionally(error -> {
                return userPresenter.prepareFailView(error.getMessage());
            });
            return userPresenter.prepareSuccessView(accountResponseModel);
        }).exceptionally(error -> {
            return userPresenter.prepareFailView(error.getMessage());
        });


    }
}
