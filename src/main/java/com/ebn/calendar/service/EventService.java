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

    public Event create(Event event) {
        return eventRepository.create(event);
    }

    public Event create(Event event, User user) {
        event.setUser(user);
        return create(event);
    }

    public Event read(String id) {
        return eventRepository.read(id);
    }

    public Event read(String id, User user) {
        Event result = read(id);
        if (result == null || !result.getUser().equals(user)) {
            return null;
        }
        return result;
    }

    public Event update(Event event) {
        return eventRepository.update(event);
    }

    public Event update(Event event, User user) {
        Event existingEvent = read(event.getId(), user);
        if (existingEvent == null) {
            return null;
        }
        event.setUser(user);
        return update(event);
    }

    public Event delete(String id) {
        return eventRepository.delete(id);
    }

    public Event delete(String id, User user) {
        Event existingEvent = read(id, user);
        if (existingEvent == null) {
            return null;
        }
        return delete(id);
    }

    public List<Event> readAll(User user) {
        return eventRepository.readUserEvents(user);
    }
}
