package com.example.cannintickets.models.events.modify;

public interface ModifyEventPresenter {
    ModifyEventResponseModel prepareSuccessView(ModifyEventResponseModel message);
    ModifyEventResponseModel prepareFailView(ModifyEventResponseModel message);

}
