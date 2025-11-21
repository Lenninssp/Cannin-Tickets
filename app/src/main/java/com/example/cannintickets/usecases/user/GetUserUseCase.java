package com.example.cannintickets.usecases.user;

import com.example.cannintickets.entities.user.signup.CommonUserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSignupFactory;
import com.example.cannintickets.entities.user.signup.UserSingupEntity;
import com.example.cannintickets.models.user.auth.UserPresenter;
import com.example.cannintickets.models.user.auth.UserResponseFormatter;
import com.example.cannintickets.models.user.auth.UserResponseModel;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.example.cannintickets.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.CompletableFuture;

public class GetUserUseCase implements GetUserInputBoundary{
    private UserRepository userRepo;
    private UserSignupFactory userFactory;
    private UserPresenter presenter;
    private UserAuthRepository authRepo;

    public GetUserUseCase(){
        this.authRepo = new UserAuthRepository();
        this.userRepo = new UserRepository();
        this.userFactory = new CommonUserSignupFactory();
        this.presenter = new UserResponseFormatter();
    }

    @Override
    public CompletableFuture<UserResponseModel> execute() {

        FirebaseUser user = authRepo.currentUser();

        if (user == null) {
            return CompletableFuture.completedFuture(
                    presenter.prepareFailView("The user is not authenticated or doesnt exist")
            );
        }
        return userRepo.get(user.getEmail()).thenApply(successUser -> {
            UserSingupEntity userEntity = userFactory.create(
                    successUser.getUsername(),
                    successUser.getEmail(),
                    "",
                    successUser.getRole()
            );

            return presenter.prepareSuccessView(new UserResponseModel(
                    successUser.getUsername(),
                    successUser.getEmail(),
                    successUser.getRole()
            ));
        }).exceptionally(error -> {
            return  presenter.prepareFailView("Error: " + error.getMessage());
        });
    }
}
