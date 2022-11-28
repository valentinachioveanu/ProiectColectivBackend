package com.ebn.calendar.controller;

import com.ebn.calendar.model.dao.Event;
import com.ebn.calendar.model.dto.request.EventCRUDRequest;
import com.ebn.calendar.model.dto.response.EventCRUDResponse;
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
    public EventCRUDResponse save(@RequestBody EventCRUDRequest request) {
        Event event = dtoToDao(request);
        Event result = eventService.save(event);
        return daoToDto(result);
    }

    @GetMapping(path = "/{id}")
    public EventCRUDResponse get(@PathVariable String id) {
        Event result = eventService.get(id);
        return daoToDto(result);
    }

    @PutMapping(path = "/{id}")
    public EventCRUDResponse update(@PathVariable String id, @RequestBody EventCRUDRequest request) {
        Event event = dtoToDao(request);
        event.setId(id);
        Event result = eventService.update(event);
        return daoToDto(result);
    }

    @DeleteMapping(path = "/{id}")
    public EventCRUDResponse delete(@PathVariable String id) {
        Event result = eventService.delete(id);
        return daoToDto(result);
    }

    @GetMapping(path = "")
    public List<EventCRUDResponse> getAll() {
        return eventService.getAll().stream().map(this::daoToDto).toList();
    }

    private Event dtoToDao(EventCRUDRequest eventRequest) {
        return modelMapper.map(eventRequest, Event.class);
    }

    private EventCRUDResponse daoToDto(Event event) {
        if (event == null) {
            return null;
        }
        return modelMapper.map(event, EventCRUDResponse.class);
    }
}
