package com.example.cannintickets.models.events.create;

public class CreateEventResponseFormatter implements CreateEventPresenter{
    @Override
    public CreateEventResponseModel prepareSuccessView(CreateEventResponseModel response) {
        return  response;
    }

    @Override
    public CreateEventResponseModel prepareFailView(CreateEventResponseModel response) {
        return response;
    }
}
