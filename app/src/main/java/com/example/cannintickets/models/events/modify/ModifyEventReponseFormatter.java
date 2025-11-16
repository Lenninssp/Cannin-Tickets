package com.example.cannintickets.models.events.modify;

import com.example.cannintickets.models.events.create.CreateEventResponseModel;

public class ModifyEventReponseFormatter implements ModifyEventPresenter {
    @Override
    public ModifyEventResponseModel prepareSuccessView(ModifyEventResponseModel response) {
        return  response;
    }

    @Override
    public ModifyEventResponseModel prepareFailView(ModifyEventResponseModel response) {
        return response;
    }
}
