package com.ebn.calendar.controller;

import com.ebn.calendar.model.dao.Event;
import com.ebn.calendar.model.dao.Tag;
import com.ebn.calendar.model.dao.User;
import com.ebn.calendar.model.dto.request.EventCRUDRequest;
import com.ebn.calendar.model.dto.request.FilterEventsRequest;
import com.ebn.calendar.model.dto.response.EventCRUDResponse;
import com.ebn.calendar.model.dto.response.MessageResponse;
import com.ebn.calendar.service.AuthService;
import com.ebn.calendar.service.EventService;
import com.ebn.calendar.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(produces = "application/json")
public class EventController {

    private final ModelMapper modelMapper;

    private final EventService eventService;

    private final TagService tagService;

    private final AuthService authService;

    @Autowired
    public EventController(ModelMapper modelMapper, EventService eventService, TagService tagService, AuthService authService) {
        this.modelMapper = modelMapper;
        this.eventService = eventService;
        this.tagService = tagService;
        this.authService = authService;
    }

    @PostMapping(path = "/events")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createEventForUser(@Valid @RequestBody EventCRUDRequest request) {
        Event event = daoFromDTO(request);
        Set<String> tagsIds = request.getTagsIds();
        User user = authService.getRequester();

        List<Tag> tags = tagService.readTagsForUser(tagsIds, user);
        if(tags==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't create event"));
        }
        event.setTags(Set.copyOf(tags));

        Event result = eventService.createEventForUser(event, user);
        if (result == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't create event"));
        }

        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @GetMapping(path = "/events/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> readEventForUser(@PathVariable String id) {
        Event result = eventService.readEventForUser(id, authService.getRequester());
        if (result == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't read event"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @PutMapping(path = "/events/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateEventForUser(@PathVariable String id, @Valid @RequestBody EventCRUDRequest request) {
        Event event = daoFromDTO(request);
        Set<String> tagsIds = request.getTagsIds();
        User user = authService.getRequester();

        event.setId(id);

        List<Tag> tags = tagService.readTagsForUser(tagsIds, user);
        if(tags==null){
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't create event"));
        }
        event.setTags(Set.copyOf(tags));

        Event result = eventService.updateEventForUser(event, user);
        if (result == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't update event"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @DeleteMapping(path = "/events/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteEventForUser(@PathVariable String id) {
        Event result = eventService.deleteEventForUser(id, authService.getRequester());
        if (result == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't delete event"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @GetMapping(path = "/events")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> readAllEventsForUser() {
        List<Event> result = eventService.readEventsForUser(authService.getRequester());
        if (result == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't read events"));
        }
        return ResponseEntity.ok()
                .body(result.stream().map(this::dtoFromDao).toList());
    }

    @PostMapping(path = "/filter-events")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> filterEventsByTags(@RequestBody FilterEventsRequest filterEventsRequest) {
        User user = authService.getRequester();
        List<Tag> requestedUserTags = tagService.readTagsForUser(filterEventsRequest.getTagsIds(), user);

        if(requestedUserTags == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't load events"));
        }

        List<Event> result = eventService.readEventsContainingAllTagsForUser(requestedUserTags, user);
        if(result == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't load events"));
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
