package com.ebn.calendar.controller;

import com.ebn.calendar.model.dao.Event;
import com.ebn.calendar.model.dto.request.EventCRUDRequest;
import com.ebn.calendar.model.dto.response.EventCRUDResponse;
import com.ebn.calendar.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public EventCRUDResponse save(@Valid @RequestBody EventCRUDRequest request) {
        Event event = daoFromDTO(request);
        Event result = eventService.save(event);
        if(result==null){
            return null;
        }
        return dtoFromDao(result);
    }

    @GetMapping(path = "/{id}")
    public EventCRUDResponse get(@PathVariable String id) {
        Event result = eventService.get(id);
        if(result==null){
            return null;
        }
        return dtoFromDao(result);
    }

    @PutMapping(path = "/{id}")
    public EventCRUDResponse update(@PathVariable String id,@Valid @RequestBody EventCRUDRequest request) {
        Event event = daoFromDTO(request);
        event.setId(id);
        Event result = eventService.update(event);
        if(result==null){
            return null;
        }
        return dtoFromDao(result);
    }

    @DeleteMapping(path = "/{id}")
    public EventCRUDResponse delete(@PathVariable String id) {
        Event result = eventService.delete(id);
        if(result==null){
            return null;
        }
        return dtoFromDao(result);
    }

    @GetMapping(path = "")
    public List<EventCRUDResponse> getAll() {
        List<Event> result=eventService.getAll();
        if(result==null){
            return null;
        }
        return result.stream().map(this::dtoFromDao).toList();
    }

    private Event daoFromDTO(EventCRUDRequest eventRequest) {
        return modelMapper.map(eventRequest, Event.class);
    }

    private EventCRUDResponse dtoFromDao(Event event) {
        return modelMapper.map(event, EventCRUDResponse.class);
    }
}
