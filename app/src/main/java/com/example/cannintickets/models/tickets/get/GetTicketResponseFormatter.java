package com.example.cannintickets.models.tickets.get;

import java.util.List;

public class GetTicketResponseFormatter implements GetTicketPresenter{
    @Override
    public List<GetTicketResponseModel> prepareSuccessView(List<GetTicketResponseModel> response) {
        return response;
    }

    @Override
    public List<GetTicketResponseModel> prepareFailView(String response) {
        return List.of(new GetTicketResponseModel(response));
    }
}
