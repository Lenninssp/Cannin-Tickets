package com.example.cannintickets.entities.ticket;

import com.example.cannintickets.services.EventValidator;
import com.example.cannintickets.services.TicketValidator;

public class CommonTicket implements TicketEntity{
    private String id;
    private String name;
    private String eventId;
    private int capacity;
    private double price;
    private int sold;

    public CommonTicket(
            String name,
            String eventId,
            int capacity,
            double price,
            int sold
    ){
        this.name = name;
        this.eventId = eventId;
        this.capacity = capacity;
        this.price = price;
        this.sold = sold;
    }
    public CommonTicket(
            String id,
            String name,
            String eventId,
            int capacity,
            double price,
            int sold
    ){
        this.id = id;
        this.name = name;
        this.eventId = eventId;
        this.capacity = capacity;
        this.price = price;
        this.sold = sold;
    }

    @Override
    public String[] isValid() {
        return TicketValidator.validateTicket(this);
    }

    @Override
    public void updateName(String newName){
        String error = TicketValidator.validateName(newName);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        this.name = newName.trim();
    }

    public void updateCapacity(int newCapacity) {
        String error = TicketValidator.validateCapacity(newCapacity);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        this.capacity = newCapacity;
    }

    public void updatePrice(double newPrice) {
        String error = TicketValidator.validatePrice(newPrice);
        if (error != null){
            throw new IllegalArgumentException(error);
        }
        this.price = newPrice;
    }
    public void updateSold(int newSold) {
        String error = TicketValidator.validateSold(newSold, this.capacity);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        this.sold = newSold;
    }


    public String getEventId() {
        return eventId;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSold() {
        return sold;
    }
}
