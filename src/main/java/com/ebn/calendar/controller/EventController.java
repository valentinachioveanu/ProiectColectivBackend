package com.ebn.calendar.controller;

import com.ebn.calendar.model.dao.Event;
import com.ebn.calendar.model.dto.request.EventCRUDRequest;
import com.ebn.calendar.model.dto.response.EventResponse;
import com.ebn.calendar.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/events", produces = "application/json")
public class EventController {

    private final ModelMapper modelMapper;
    private final EventService eventService;

    @Autowired
    public EventController(ModelMapper modelMapper, EventService eventService) {
        this.modelMapper = modelMapper;
        this.eventService = eventService;
    }

    @PostMapping(path = "")
    public EventResponse save(@RequestBody EventCRUDRequest request) {
        Event event = dtoToDao(request);
        Event result = eventService.save(event);
        return daoToDto(result);
    }

    @GetMapping(path = "/{id}")
    public EventResponse get(@PathVariable String id) {
        Event result = eventService.get(id);
        return daoToDto(result);
    }

    @PutMapping(path = "/{id}")
    public EventResponse update(@PathVariable String id, @RequestBody EventCRUDRequest request) {
        Event event = dtoToDao(request);
        event.setId(id);
        Event result = eventService.update(event);
        return daoToDto(result);
    }

    @DeleteMapping(path = "/{id}")
    public EventResponse delete(@PathVariable String id) {
        Event result = eventService.delete(id);
        return daoToDto(result);
    }

    @GetMapping(path = "")
    public List<EventResponse> getAll() {
        return eventService.getAll().stream().map(this::daoToDto).toList();
    }

    private Event dtoToDao(EventCRUDRequest eventRequest) {
        return modelMapper.map(eventRequest, Event.class);
    }

    private EventResponse daoToDto(Event event) {
        if (event == null) {
            return null;
        }
        return modelMapper.map(event, EventResponse.class);
    }
}
