package com.example.cannintickets.models.tickets.modify;

public class ModifyTicketRequestModel {
    private final String id;
    private final String name;
    private final Integer capacity;
    private final Integer price;

    public ModifyTicketRequestModel(
            String id,
            String name,
            Integer capacity,
            Integer price
    ){
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.price = price;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getPrice() {
        return price;
    }
}
