package com.ebn.calendar.controllers;

import com.ebn.calendar.model.Event;
import com.ebn.calendar.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/events", produces = "application/json")
public class EventsController {

    public final EventRepository eventRepository;

    @Autowired
    public EventsController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping(path="")
    public Event save(@RequestBody Event entity) {
        return eventRepository.save(entity);
    }

    @GetMapping(path="/{id}")
    public Event get(@PathVariable String id){
        return eventRepository.get(id);
    }

    @PutMapping(path="/{id}")
    public Event update(@PathVariable String id, @RequestBody Event entity) {
        entity.setId(id);
        return eventRepository.update(entity);
    }

    @DeleteMapping(path="/{id}")
    public Event delete(@PathVariable String id) {
        return eventRepository.delete(id);
    }

    @GetMapping(path="")
    public List<Event> getAll() {
        return eventRepository.getAll();
    }
}
