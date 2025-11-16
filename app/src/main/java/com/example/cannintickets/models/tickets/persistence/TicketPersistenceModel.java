package com.example.cannintickets.models.tickets.persistence;

public class TicketPersistenceModel {
    String id;
    String name;
    String eventId;
    int capacity;
    int sold;
    double price;

    public TicketPersistenceModel(){}
    public TicketPersistenceModel(
        String id,
        String name,
        String eventId,
        int capacity,
        int sold,
        double price
    ){
        this.id = id;
        this.name = name;
        this.eventId = eventId;
        this.capacity = capacity;
        this.sold = sold;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public int getSold() {
        return sold;
    }

    public String getEventId() {
        return eventId;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }
}
