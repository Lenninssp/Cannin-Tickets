package com.example.cannintickets.models.orders;

import java.util.List;

public class OrderResponseFormatter implements OrderPresenter{
    @Override
    public List<OrderResponseModel> prepareSuccessView(List<OrderResponseModel> orders){
        return orders;
    }

    @Override
    public List<OrderResponseModel> prepareFailView(String response) {
        return List.of(new OrderResponseModel(response));
    }
}
