package com.ebn.calendar.controller;

import com.ebn.calendar.model.dao.Tag;
import com.ebn.calendar.model.dto.request.TagCRUDRequest;
import com.ebn.calendar.model.dto.response.MessageResponse;
import com.ebn.calendar.model.dto.response.TagCRUDResponse;
import com.ebn.calendar.service.AuthService;
import com.ebn.calendar.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/tags", produces = "application/json")
public class TagController {

    private final ModelMapper modelMapper;

    private final TagService tagService;

    private final AuthService authService;

    @Autowired
    public TagController(ModelMapper modelMapper, TagService tagService, AuthService authService) {
        this.modelMapper = modelMapper;
        this.tagService = tagService;
        this.authService = authService;
    }

    @PostMapping(path = "")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> create(@Valid @RequestBody TagCRUDRequest request) {
        Tag tag = daoFromDTO(request);

        Tag result = tagService.create(tag, authService.getRequester());
        if (result == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't create tag"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> read(@PathVariable String id) {
        Tag result = tagService.read(id, authService.getRequester());
        if (result == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't read tag"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody TagCRUDRequest request) {
        Tag tag = daoFromDTO(request);
        tag.setId(id);
        Tag result = tagService.update(tag, authService.getRequester());
        if (result == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't update tag"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Tag result = tagService.delete(id, authService.getRequester());
        if (result == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't delete tag"));
        }
        return ResponseEntity.ok()
                .body(dtoFromDao(result));
    }

    @Deprecated
    @GetMapping(path = "")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> readAll() {
        List<Tag> result = tagService.readAll(authService.getRequester());
        if (result == null) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("Error: couldn't read tags"));
        }
        return ResponseEntity.ok()
                .body(result.stream().map(this::dtoFromDao).toList());
    }

    private Tag daoFromDTO(TagCRUDRequest tagRequest) {
        return modelMapper.map(tagRequest, Tag.class);
    }

    private TagCRUDResponse dtoFromDao(Tag tag) {
        return modelMapper.map(tag, TagCRUDResponse.class);
    }
}
