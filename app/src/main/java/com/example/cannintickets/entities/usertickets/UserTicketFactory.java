package com.example.cannintickets.entities.usertickets;

import java.util.Date;

public interface UserTicketFactory {
    UserTicketEntity create(
            String id,
            Boolean checked,
            Date eventDate,
            String eventId,
            String eventName,
            String location,
            String ticketId,
            String ticketName,
            String userEmail
    );
}
