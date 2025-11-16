package com.example.cannintickets.models.events.delete;

public class DeleteEventResponseFormatter implements DeleteEventPresenter {
    @Override
    public DeleteEventResponseModel prepareSuccessView(DeleteEventResponseModel response) {
        return  response;
    }

    @Override
    public DeleteEventResponseModel prepareFailView(DeleteEventResponseModel response) {
        return response;
    }
}
