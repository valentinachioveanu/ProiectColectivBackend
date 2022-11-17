package com.ebn.calendar.controllers;

import com.ebn.calendar.model.Event;
import com.ebn.calendar.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/events")
public class EventsController {

    public final EventRepository eventRepository;

    @Autowired
    public EventsController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping(path="")
    @PreAuthorize("hasRole('ADMIN')")
    public Event save(@RequestBody Event entity) {
        return eventRepository.save(entity);
    }

    @GetMapping(path="/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Event get(@PathVariable String id){
        return eventRepository.get(id);
    }

    @PutMapping(path="/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Event update(@PathVariable String id, @RequestBody Event entity) {
        entity.setId(id);
        return eventRepository.update(entity);
    }

    @DeleteMapping(path="/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Event delete(@PathVariable String id) {
        return eventRepository.delete(id);
    }

    @GetMapping(path="")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Event> getAll() {
        return eventRepository.getAll();
    }
}
