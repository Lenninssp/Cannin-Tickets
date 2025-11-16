package com.example.cannintickets.usecases.events.get;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventEntity;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.models.events.create.response.CreateEventResponseModel;
import com.example.cannintickets.models.events.get.presenter.GetEventPresenter;
import com.example.cannintickets.models.events.get.presenter.GetEventResponseFormatter;
import com.example.cannintickets.models.events.get.response.GetEventResponseModel;
import com.example.cannintickets.models.events.persistence.EventPersistenceModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetEventUseCase implements GetEventInputBoundary{
    final EventRepository eventRepo;
    final UserAuthRepository authRepo;
    final EventFactory eventFactory;
    final GetEventPresenter eventPresenter;

    public GetEventUseCase(){
        this.eventFactory = new CommonEventFactory();
        this.authRepo = new UserAuthRepository();
        this.eventRepo = new EventRepository();
        this.eventPresenter = new GetEventResponseFormatter();
    }

    @Override
    public CompletableFuture<List<GetEventResponseModel>> execute() {
        List<GetEventResponseModel> returnList = new ArrayList<>();
        FirebaseUser user = authRepo.currentUser();
        if (user == null) {
            return CompletableFuture.completedFuture(
                    eventPresenter.prepareFailView(
                            "The user is not authenticated or doesnt exist"
                    )
            );
        }

        return eventRepo.getPublic().thenApply( successMessage -> {

            for(EventPersistenceModel event : successMessage) {
                LocalDateTime eventDate = LocalDateTime.parse(event.getEventDate().toString());
                EventEntity eventEntity = eventFactory.create(
                        event.getName(),
                        event.getDescription(),
                        eventDate,
                        event.getLocation()
                );

                // todo: return event image here
                if (eventEntity.isEventInTheFuture()){
                    returnList.add(new GetEventResponseModel(
                            event.getId(),
                            event.getName(),
                            event.getDescription(),
                            event.getEventDate(),
                            event.getLocation(),
                            new File("")
                    ));
                }
            }
            return returnList;
        }).exceptionally(error -> {
            return eventPresenter.prepareFailView(
                            "There was an error: " + error.getMessage()
                    );

        });
    }
}
