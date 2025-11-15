package com.example.cannintickets.models.events.presenter;

import com.example.cannintickets.models.events.response.CreateEventResponseModel;

public interface CreateEventPresenter {
    CreateEventResponseModel prepareSuccessView(CreateEventResponseModel message);
    CreateEventResponseModel prepareFailView(CreateEventResponseModel error);
}
