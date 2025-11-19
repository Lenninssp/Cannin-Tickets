package com.example.cannintickets.models.usertickets;

import java.util.List;

public interface UserTicketPresenter  {
    List<UserTicketsResponseModel> prepareSuccessView(List<UserTicketsResponseModel> tickets);
    List<UserTicketsResponseModel> prepareFailView(String message);
}
