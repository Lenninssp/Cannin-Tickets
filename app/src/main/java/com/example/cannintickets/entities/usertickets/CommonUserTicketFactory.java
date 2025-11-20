package com.example.cannintickets.entities.usertickets;

import java.time.LocalDateTime;
import java.util.Date;

public class CommonUserTicketFactory implements UserTicketFactory {
    @Override
    public UserTicketEntity create(
            Boolean checked, LocalDateTime eventDate, String eventId, String eventName, String location, String ticketId, String ticketName, String userEmail
    ) {
        return new CommonUserTicket(
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
