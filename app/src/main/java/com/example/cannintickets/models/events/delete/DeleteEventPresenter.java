package com.example.cannintickets.models.events.delete;


public interface DeleteEventPresenter {
    DeleteEventResponseModel prepareSuccessView(DeleteEventResponseModel message);
    DeleteEventResponseModel prepareFailView(DeleteEventResponseModel message);
}
