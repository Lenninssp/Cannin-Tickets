package com.example.cannintickets.models.saves;

public class SaveResponseFormatter implements SavePresenter{
    @Override
    public SaveResponseModel prepareSuccessView(Boolean save){
        return new SaveResponseModel(save);
    }

    @Override
    public SaveResponseModel prepareFailView(String message){
        return new SaveResponseModel(message);
    }
}
