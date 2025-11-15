package com.example.cannintickets.boundaries.input;

import com.example.cannintickets.models.request.UserSignupRequestModel;
import com.example.cannintickets.models.response.UserSignupResponseModel;

public interface UserInputBoundary {
    UserSignupResponseModel create(UserSignupRequestModel requestModel);
}
