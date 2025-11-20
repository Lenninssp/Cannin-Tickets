package com.example.cannintickets.models.orders;

import java.util.List;

public interface OrderPresenter {
    List<OrderResponseModel> prepareSuccessView(List<OrderResponseModel> orders);
    List<OrderResponseModel> prepareFailView(String message);
}
