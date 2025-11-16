package com.example.cannintickets.services;

import com.example.cannintickets.entities.ticket.TicketEntity;
import com.example.cannintickets.entities.ticket.TicketName;

public class TicketValidator {

    public static String[] validateTicket(TicketEntity ticket) {
        String nameError = validateName(ticket.getName());
        String capacityError = validateCapacity(ticket.getCapacity());
        String soldError = validateSold(ticket.getSold(), ticket.getCapacity());
        String priceError = validatePrice(ticket.getPrice());

        if (nameError != null) {
            return new String[]{"Error", nameError};
        }
        if (capacityError != null) {
            return new String[]{"Error", capacityError};
        }
        if (soldError != null) {
            return new String[]{"Error", soldError};
        }
        if (priceError != null) {
            return new String[]{"Error", priceError};
        }
        return new String[]{"SUCCESS", "The ticket is valid."};
    }

    public static String validateName(String name) {
        if (name == null || name.trim().isEmpty()){
            return "The ticket name cannot be empty. Please provide a title for the ticket";
        }
        if(TicketName.findByName(name) == null) {
            return "The ticket name is not within the selection of predefined names";
        }
        return null;
    }

    public static String validateCapacity(int capacity){
        if (capacity <= 0){
            return "The event capacity is not valid, it must be more than 1";
        }
        return null;
    }

    public static String validateSold(int sold, int capacity) {
        if (sold > capacity) {
            return "The ticket has surpassed it's limit of places";
        }
        return null;
    }

    public static String validatePrice(double price) {
        if (price < 0){
            return "Error, the price can not be lower than $0";
        }
        return null;
    }
}
