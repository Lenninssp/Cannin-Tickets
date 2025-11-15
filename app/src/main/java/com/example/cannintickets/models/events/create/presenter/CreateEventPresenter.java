package com.example.cannintickets.models.events.create.presenter;

import com.example.cannintickets.models.events.create.response.CreateEventResponseModel;

public interface CreateEventPresenter {
    CreateEventResponseModel prepareSuccessView(CreateEventResponseModel message);
    CreateEventResponseModel prepareFailView(CreateEventResponseModel error);
}
