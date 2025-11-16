package com.example.cannintickets.models.events.get;

import java.util.List;

public class GetEventResponseFormatter implements GetEventPresenter{
    @Override
    public List<GetEventResponseModel> prepareSuccessView(List<GetEventResponseModel> response) {
        return response;
    }

    @Override
    public List<GetEventResponseModel> prepareFailView(String response) {
        return List.of(new GetEventResponseModel(response));
    }
}
