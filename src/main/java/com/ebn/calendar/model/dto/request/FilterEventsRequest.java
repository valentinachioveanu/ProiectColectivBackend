package com.ebn.calendar.model.dto.request;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class FilterEventsRequest {

    @NotNull(message = "'tagsIds' is mandatory")
    private Set<String> tagsIds;

    public Set<String> getTagsIds() {
        return tagsIds;
    }

    public void setTagsIds(Set<String> tagsIds) {
        this.tagsIds = tagsIds;
    }
}
