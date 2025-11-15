package com.example.cannintickets.entities.event;

import java.time.LocalDateTime;

public interface EventEntity {
    boolean isEventInTheFuture();
    boolean isNameValid();
    boolean isDescriptionValid();
    boolean isLocationValid();
    String[] isValid();
}
