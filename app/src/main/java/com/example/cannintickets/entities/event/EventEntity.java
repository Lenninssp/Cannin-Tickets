package com.example.cannintickets.entities.event;

import java.time.LocalDateTime;

public interface EventEntity {

    boolean canSellGA(int quantity);
    boolean canSellVIP(int quantity);
    void sellGA(int quantity);
    void sellVIP(int quantity);
    boolean isSoldOut();
    boolean isEditable();
    boolean isPublic();
    void reschedule(LocalDateTime newDate);
}
