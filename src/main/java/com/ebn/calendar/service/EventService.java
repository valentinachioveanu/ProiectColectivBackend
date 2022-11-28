package com.ebn.calendar.service;

import com.ebn.calendar.model.dao.Event;
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

    public Event read(String id) {
        return eventRepository.read(id);
    }

    public Event update(Event event) {
        return eventRepository.update(event);
    }

    public Event delete(String id) {
        return eventRepository.delete(id);
    }

    @Deprecated
    public List<Event> readAll() {
        return eventRepository.readAll();
    }
}