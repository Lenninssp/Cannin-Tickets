package com.example.cannintickets.models.usertickets;

import java.util.List;

public class UserTicketResponseFormatter implements UserTicketPresenter{
    @Override
    public List<UserTicketsResponseModel> prepareSuccessView(List<UserTicketsResponseModel> tickets){
        return tickets;
    }

    @Override
    public List<UserTicketsResponseModel> prepareFailView(String response) {
        return List.of(new UserTicketsResponseModel(response));
    }

}
