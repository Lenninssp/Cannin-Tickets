package com.example.cannintickets.entities.event;

import java.time.LocalDateTime;

public class CommonEventFactory implements EventFactory{
    @Override
    public EventEntity create(
            String name,
            String description,
            LocalDateTime eventDate,
            String location
    ){
        return new CommonEvent(name, description, eventDate, location);
    }
}
