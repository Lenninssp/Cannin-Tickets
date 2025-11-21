package com.example.cannintickets.models.saves;

import java.util.List;

public interface SavePresenter {
    SaveResponseModel prepareSuccessView(Boolean save);
    SaveResponseModel prepareFailView(String message);
}
