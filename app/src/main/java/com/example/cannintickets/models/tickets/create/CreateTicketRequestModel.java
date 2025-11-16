package com.example.cannintickets.models.tickets.create;

public class CreateTicketRequestModel {
    private final String name;
    private final String eventId;
    private final int capacity;
    private final double price;

    public CreateTicketRequestModel (
            String name,
            String eventId,
            int capacity,
            double price
    ) {
        this.name = name;
        this.eventId = eventId;
        this.capacity = capacity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getEventId() {
        return eventId;
    }
}
