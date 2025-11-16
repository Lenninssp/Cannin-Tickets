package com.example.cannintickets.entities.event;

import java.time.LocalDateTime;

public class CommonEventFactory implements EventFactory {

    @Override
    public EventEntity create(
            String name,
            String description,
            LocalDateTime eventDate,
            String location,
            Boolean isPrivate,
            String organizerId
    ) {
        return new CommonEvent(
                name,
                description,
                eventDate,
                location,
                isPrivate,
                organizerId
        );
    }

    @Override
    public EventEntity createFromPersistence(
            String id,
            String name,
            String description,
            LocalDateTime eventDate,
            String location,
            Boolean isPrivate,
            String organizerId
    ) {
        return new CommonEvent(
                id,
                name,
                description,
                eventDate,
                location,
                isPrivate,
                organizerId
        );
    }
}