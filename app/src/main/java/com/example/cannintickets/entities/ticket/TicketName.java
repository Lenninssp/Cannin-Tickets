package com.example.cannintickets.entities.ticket;

public enum TicketName {
    GA,
    VIP,
    BRONZE,
    SILVER,
    GOLD,
    DIAMOND;

    public static TicketName findByName(String name) {
        TicketName ticket = null;
        for (TicketName t : values()){
            if (t.name().equalsIgnoreCase(name));
            ticket = t;
        }
        return ticket;
    }
}
