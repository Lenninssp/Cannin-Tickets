package com.example.cannintickets.usecases.events;

import com.example.cannintickets.entities.event.CommonEventFactory;
import com.example.cannintickets.entities.event.EventEntity;
import com.example.cannintickets.entities.event.EventFactory;
import com.example.cannintickets.entities.image.CommonImageFactory;
import com.example.cannintickets.entities.image.ImageEntity;
import com.example.cannintickets.models.events.persistence.EventPersistenceModel;
import com.example.cannintickets.models.events.create.presenter.CreateEventPresenter;
import com.example.cannintickets.models.events.create.presenter.CreateEventResponseFormatter;
import com.example.cannintickets.models.events.create.request.CreateEventRequestModel;
import com.example.cannintickets.models.events.create.response.CreateEventResponseModel;
import com.example.cannintickets.repositories.EventRepository;
import com.example.cannintickets.repositories.ImageRepository;
import com.example.cannintickets.repositories.UserAuthRepository;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class CreateEventUseCase implements CreateEventInputBundary {
    final EventRepository repo;
    final UserAuthRepository authRepo;
    final ImageRepository imageRepo;
    final CreateEventPresenter eventPresenter;
    final EventFactory eventFactory;
    final CommonImageFactory imageFactory;

    public CreateEventUseCase() {
        this.repo = new EventRepository();
        this.eventPresenter = new CreateEventResponseFormatter();
        this.authRepo = new UserAuthRepository();
        this.eventFactory = new CommonEventFactory();
        this.imageRepo = new ImageRepository();
        this.imageFactory = new CommonImageFactory();
    }

    public CompletableFuture<CreateEventResponseModel> execute(CreateEventRequestModel requestModel) {
        FirebaseUser user = authRepo.currentUser();
        if (user == null) {
            return CompletableFuture.completedFuture(
                    eventPresenter.prepareFailView(new CreateEventResponseModel(
                            "The user doesn't exist or isn't authenticated",
                            false
                    ))
            );
        }

        //todo: here I should check the user table to check the permissions

        ImageEntity image = imageFactory.create(
                requestModel.getCoverImage()
        );


//        if (!image.isValid()) {
//            return CompletableFuture.completedFuture(
//                    eventPresenter.prepareFailView(
//                            new CreateEventResponseModel(
//                                    "The image is not valid",
//                                    false
//                            )
//                    )
//            );
//        }
        // todo: make a better image upload implementation because this one aint it


//        String[] imageURL = imageRepo.create(requestModel.getCoverImage());

        //taken from: https://stackoverflow.com/questions/41992970/how-do-i-convert-an-iso-8601-date-time-string-to-java-time-localdatetime
        LocalDateTime eventDate = LocalDateTime.parse(requestModel.getEventDate());

        EventEntity event = eventFactory.create(
                requestModel.getName(),
                requestModel.getDescription(),
                eventDate,
                requestModel.getLocation()
        );


        if (event.isValid()[0].equals("ERROR")) {
            return CompletableFuture.completedFuture(
                    eventPresenter.prepareFailView(
                            new CreateEventResponseModel(
                                    event.isValid()[1],
                                    false
                            )

                    )
            );
        }

        EventPersistenceModel eventPersisted = new EventPersistenceModel(
                requestModel.getName(),
                requestModel.getDescription(),
                requestModel.getEventDate(),
                requestModel.getLocation(),
                requestModel.isPrivate(),
                "",
                user.getEmail()
        );

        return repo.create(eventPersisted).thenApply(successMesssage -> {
            return eventPresenter.prepareSuccessView(new CreateEventResponseModel(
                    "The event was created successfully",
                    true
            ));
        }).exceptionally(errorMessage -> {
            return eventPresenter.prepareSuccessView(new CreateEventResponseModel(
                    errorMessage.getMessage(),
                    false
            ));
        });
    }
}
