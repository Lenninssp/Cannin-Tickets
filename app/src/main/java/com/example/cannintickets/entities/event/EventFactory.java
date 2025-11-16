package com.example.cannintickets.entities.event;

import java.time.LocalDateTime;

public interface EventFactory {
    EventEntity create(
            String name,
            String description,
            LocalDateTime eventDate,
            String location,
            Boolean isPrivate,
            String organizerId
    );

    EventEntity createFromPersistence(
            String id,
            String name,
            String description,
            LocalDateTime eventDate,
            String location,
            Boolean isPrivate,
            String organizerId
    );
}