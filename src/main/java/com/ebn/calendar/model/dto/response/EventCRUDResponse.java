package com.ebn.calendar.model.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

public class EventCRUDResponse {
    private String id;

    private String title;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean allDay;

    private Set<TagCRUDResponse> tags;

    public EventCRUDResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public Set<TagCRUDResponse> getTags() {
        return tags;
    }

    public void setTags(Set<TagCRUDResponse> tags) {
        this.tags = tags;
    }
}
