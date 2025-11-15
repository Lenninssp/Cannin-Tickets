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
import com.example.cannintickets.services.AuthService;

public class SignupUseCase implements UserInputBoundary {
    final UserFactory userFactory;
    final UserPresenter userPresenter;

    public SignupUseCase() {
        this.userFactory = new CommonUserFactory();
        this.userPresenter = new UserResponseFormatter();
    }

    @Override
    public UserSignupResponseModel create(UserSignupRequestModel requestModel) {

        System.out.println("[INFO] Starting user signup flow...");
        System.out.println("[DEBUG] Incoming request model: username=" + requestModel.getUsername()
                + ", email=" + requestModel.getEmail()
                + ", role=" + requestModel.getRole());

        // todo: check if user doesn't exist
        System.out.println("[INFO] Creating user entity from factory...");
        UserEntity user = userFactory.create(
                requestModel.getUsername(),
                requestModel.getEmail(),
                requestModel.getPassword(),
                requestModel.getRole()
        );

        System.out.println("[DEBUG] User entity created: username=" + user.getUsername()
                + ", email=" + user.getEmail()
                + ", role=" + user.getRole());

        if (!user.isValid()) {
            System.out.println("[WARN] User validation failed. Rejecting signup.");
            return userPresenter.prepareFailView("The user is not valid");
        }

        System.out.println("[INFO] User is valid. Preparing repository request model...");
        UserSignupRequestModel userRsModel = new UserSignupRequestModel(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );

        System.out.println("[DEBUG] Repository request model prepared. Saving to database...");

        UserRepository repo = new UserRepository();
        String[] response = repo.save(userRsModel);

        if (response[0].equals("ERROR")) {
            System.out.println("[ERROR] Repository failed: " + response[1]);
            return userPresenter.prepareFailView(response[1]);
        }

        System.out.println("[INFO] Repository save successful. Preparing response model...");

        UserSignupResponseModel accountResponseModel =
                new UserSignupResponseModel(user.getUsername(), user.getEmail(), user.getRole());

        System.out.println("[DEBUG] Response model created. username=" + accountResponseModel.getUsername()
                + ", email=" + accountResponseModel.getEmail()
                + ", role=" + accountResponseModel.getRole());

        System.out.println("[INFO] Returning success view to presenter.");
        return userPresenter.prepareSuccessView(accountResponseModel);
    }
}
