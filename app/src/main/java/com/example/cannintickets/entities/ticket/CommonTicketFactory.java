package com.example.cannintickets.entities.ticket;

public class CommonTicketFactory implements TicketFactory{

    @Override
    public TicketEntity create(
            String name,
            String eventId,
            int capacity,
            double price,
            int sold
    ){
        return new CommonTicket(
                name,
                eventId,
                capacity,
                price,
                sold
        );
    }

    @Override
    public TicketEntity createFromPersistence(
            String id,
            String name,
            String eventId,
            int capacity,
            double price,
            int sold
    ){
        return new CommonTicket(
                id,
                name,
                eventId,
                capacity,
                price,
                sold
        );
    }
}
