package com.example.cannintickets.models.events.create;

public interface CreateEventPresenter {
    CreateEventResponseModel prepareSuccessView(CreateEventResponseModel message);
    CreateEventResponseModel prepareFailView(CreateEventResponseModel error);
}
