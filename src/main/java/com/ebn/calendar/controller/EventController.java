package com.ebn.calendar.controller;

import com.ebn.calendar.model.dao.Event;
import com.ebn.calendar.model.dto.request.EventCRUDRequest;
import com.ebn.calendar.model.dto.response.EventCRUDResponse;
import com.ebn.calendar.model.dto.response.MessageResponse;
import com.ebn.calendar.service.AuthService;
import com.ebn.calendar.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/events", produces = "application/json")
public class EventController {

    private final ModelMapper modelMapper;
    private final EventService eventService;

    private final AuthService authService;

    @Autowired
    public EventController(ModelMapper modelMapper, EventService eventService, AuthService authService) {
        this.modelMapper = modelMapper;
        this.eventService = eventService;
        this.authService = authService;
    }

    @PostMapping(path = "")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> create(@Valid @RequestBody EventCRUDRequest request) {
        Event event = daoFromDTO(request);

        Event result = eventService.create(event,authService.getRequester());
        if(result==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't create event"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> read(@PathVariable String id) {
        Event result = eventService.read(id,authService.getRequester());
        if(result==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't read event"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> update(@PathVariable String id,@Valid @RequestBody EventCRUDRequest request) {
        Event event = daoFromDTO(request);
        event.setId(id);
        Event result = eventService.update(event,authService.getRequester());
        if(result==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't update event"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Event result = eventService.delete(id,authService.getRequester());
        if(result==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't delete event"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @Deprecated
    @GetMapping(path = "")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> readAll() {
        List<Event> result=eventService.readAll(authService.getRequester());
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
