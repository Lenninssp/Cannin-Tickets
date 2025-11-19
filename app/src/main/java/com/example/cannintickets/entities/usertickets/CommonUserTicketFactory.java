package com.example.cannintickets.entities.usertickets;

import java.util.Date;

public class CommonUserTicketFactory implements UserTicketFactory {
    @Override
    public UserTicketEntity create(
            String id, Boolean checked, Date eventDate, String eventId, String eventName, String location, String ticketId, String ticketName, String userEmail
    ) {
        return new CommonUserTicket(
                id,
                checked,
                eventDate,
                eventId,
                eventName,
                location,
                ticketId,
                ticketName,
                userEmail
        );
    }
}
