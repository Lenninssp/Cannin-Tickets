package com.example.cannintickets.entities.ticket;

public interface TicketEntity {

    String[] isValid();

    void updateName(String newName);
    void updateCapacity(int newCapacity);
    void updatePrice(double newPrice);
    void updateSold(int newSold);

    String getName();
    int getCapacity();
    double getPrice();
    int getSold();
}
