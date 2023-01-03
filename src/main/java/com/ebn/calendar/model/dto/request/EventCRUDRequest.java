package com.ebn.calendar.model.dto.request;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Set;

public class EventCRUDRequest {
    @NotNull(message = "'title' is mandatory")
    @Size(max = 20, message = "'title' should have a maximum length 20 character")
    @NotEmpty(message = "'title' cannot be empty")
    private String title;

    @NotNull(message = "'description' is mandatory")
    @Size(max = 100, message = "'description' should have a maximum length 100 character")
    private String description;

    @NotNull(message = "'startDate' is mandatory")
    private Timestamp startDate;

    @NotNull(message = "'endDate' is mandatory")
    private Timestamp endDate;

    private Boolean allDay;

    @NotNull(message = "'tagsIds' is mandatory")
    private Set<String> tagsIds;

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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public Set<String> getTagsIds() {
        return tagsIds;
    }

    public void setTagsIds(Set<String> tagsIds) {
        this.tagsIds = tagsIds;
    }

    @AssertTrue(message = "'startDate' should be before 'endDate'")
    public boolean isValidRange() {
        if (endDate == null || startDate == null) {
            return false;
        }
        return endDate.compareTo(startDate) >= 0;
    }
}
