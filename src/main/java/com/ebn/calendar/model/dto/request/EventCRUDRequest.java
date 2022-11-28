package com.ebn.calendar.model.dto.request;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventCRUDRequest {
    @NotNull(message = "'title' is mandatory")
    private String title;

    @NotNull(message = "'description' is mandatory")
    private String description;

    @NotNull(message = "'startDate' is mandatory")
    private LocalDateTime startDate;

    @NotNull(message = "'endDate' is mandatory")
    private LocalDateTime endDate;

    private Boolean allDay;

    public EventCRUDRequest() {
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

    @AssertTrue(message = "'startDate' should be before 'endDate'")
    public boolean isValidRange() {
        if(endDate==null || startDate==null){
            return false;
        }
        return endDate.compareTo(startDate) >= 0;
    }
}
