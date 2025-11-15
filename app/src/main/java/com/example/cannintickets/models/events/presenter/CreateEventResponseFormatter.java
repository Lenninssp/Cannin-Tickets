package com.example.cannintickets.models.events.presenter;

import com.example.cannintickets.models.events.request.CreateEventRequestModel;
import com.example.cannintickets.models.events.response.CreateEventResponseModel;

public class CreateEventResponseFormatter implements  CreateEventPresenter{
    @Override
    public CreateEventResponseModel prepareSuccessView(CreateEventResponseModel response) { return  response; }

    @Override
    public CreateEventResponseModel prepareFailView(CreateEventResponseModel response) { return response; }
}
