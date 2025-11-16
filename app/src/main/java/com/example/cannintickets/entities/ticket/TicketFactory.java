package com.example.cannintickets.entities.ticket;

public interface TicketFactory {
    TicketEntity create(
            String name,
            String eventId,
            int capacity,
            double price,
            int sold
    );

    TicketEntity createFromPersistence(
            String id,
            String name,
            String eventId,
            int capacity,
            double price,
            int sold
    );
}
