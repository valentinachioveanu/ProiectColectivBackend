package com.ebn.calendar.controller;

import com.ebn.calendar.model.dao.Event;
import com.ebn.calendar.model.dto.request.EventCRUDRequest;
import com.ebn.calendar.model.dto.response.EventCRUDResponse;
import com.ebn.calendar.model.dto.response.MessageResponse;
import com.ebn.calendar.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> create(@Valid @RequestBody EventCRUDRequest request) {
        Event event = daoFromDTO(request);
        Event result = eventService.create(event);
        if(result==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't create event"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> read(@PathVariable String id) {
        Event result = eventService.read(id);
        if(result==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't read event"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> update(@PathVariable String id,@Valid @RequestBody EventCRUDRequest request) {
        Event event = daoFromDTO(request);
        event.setId(id);
        Event result = eventService.update(event);
        if(result==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't update event"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Event result = eventService.delete(id);
        if(result==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't delete event"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @Deprecated
    @GetMapping(path = "")
    public ResponseEntity<?> readAll() {
        List<Event> result=eventService.readAll();
        if(result==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't read events"));
        }
        return ResponseEntity.ok()
                .body(result.stream().map(this::dtoFromDao).toList());
    }

    private Event daoFromDTO(EventCRUDRequest eventRequest) {
        return modelMapper.map(eventRequest, Event.class);
    }

    private EventCRUDResponse dtoFromDao(Event event) {
        return modelMapper.map(event, EventCRUDResponse.class);
    }
}
