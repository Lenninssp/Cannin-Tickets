package com.example.cannintickets.models.events.get.presenter;

import com.example.cannintickets.models.events.get.response.GetEventResponseModel;

import java.util.List;

public interface GetEventPresenter {
    List<GetEventResponseModel> prepareSuccessView(List<GetEventResponseModel> events);
    List<GetEventResponseModel> prepareFailView(String message);
}
