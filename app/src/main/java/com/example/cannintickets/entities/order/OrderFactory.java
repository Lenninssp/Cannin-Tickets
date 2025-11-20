package com.example.cannintickets.entities.order;

import java.time.LocalDateTime;

public interface OrderFactory {
    OrderEntity create(
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Double total,
            String customerEmail,
            String eventId,
            String eventName,
            String paymentIntentId,
            Integer quantity,
            String ticketId,
            String ticketName,
            Double ticketPrice
    );
}
