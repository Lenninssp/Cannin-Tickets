package com.example.cannintickets.models.saves;

import com.example.cannintickets.repositories.SaveRepository;

public class SaveResponseModel {
    private Boolean result;
    private String message;

    public SaveResponseModel(String message){
        this.message = message;
    }
    public SaveResponseModel(Boolean result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return message != null && !message.isEmpty();
    }

    public Boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
