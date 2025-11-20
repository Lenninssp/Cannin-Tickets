package com.example.cannintickets.entities.order;

import java.time.LocalDateTime;

public class CommonOrderFactory implements OrderFactory{
    @Override
    public OrderEntity create(
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
    ) {
        return new CommonOrder(
                createdAt,
                updatedAt,
                total,
                customerEmail,
                eventId,
                eventName,
                paymentIntentId,
                quantity,
                ticketId,
                ticketName,
                ticketPrice
        );
    }
}
