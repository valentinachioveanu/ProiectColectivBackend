package com.ebn.calendar.service;

import com.ebn.calendar.model.dao.Event;
import com.ebn.calendar.model.dao.User;
import com.ebn.calendar.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEventForUser(Event event, User user) {
        event.setOwner(user);
        return eventRepository.create(event);
    }

    public Event readEventForUser(String id, User user) {
        Event result = eventRepository.read(id);
        if (result == null || !result.getOwner().equals(user)) {
            return null;
        }
        return result;
    }

    public Event updateEventForUser(Event event, User user) {
        Event existingEvent = readEventForUser(event.getId(), user);
        if (existingEvent == null) {
            return null;
        }
        event.setOwner(user);
        return eventRepository.update(event);
    }

    public Event deleteEventForUser(String id, User user) {
        Event existingEvent = readEventForUser(id, user);
        if (existingEvent == null) {
            return null;
        }
        return eventRepository.delete(id);
    }

    public List<Event> readEventsForUser(User user) {
        return eventRepository.readUserEvents(user);
    }
}
