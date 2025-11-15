package com.example.cannintickets.models.events.create.presenter;

import com.example.cannintickets.models.events.create.response.CreateEventResponseModel;

public class CreateEventResponseFormatter implements CreateEventPresenter{
    @Override
    public CreateEventResponseModel prepareSuccessView(CreateEventResponseModel response) { return  response; }

    @Override
    public CreateEventResponseModel prepareFailView(CreateEventResponseModel response) { return response; }
}
