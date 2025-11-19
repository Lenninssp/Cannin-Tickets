package com.example.cannintickets.models.tickets.get;

import java.util.List;

public interface GetTicketPresenter {
    List<GetTicketResponseModel> prepareSuccessView(List<GetTicketResponseModel> tickets);
    List<GetTicketResponseModel> prepareFailView(String message);
}
