package com.ebn.calendar.model.dto.request;

import java.util.Set;

public class FilterEventsRequest {
    private Set<String> tagsIds;

    public Set<String> getTagsIds() {
        return tagsIds;
    }

    public void setTagsIds(Set<String> tagsIds) {
        this.tagsIds = tagsIds;
    }
}
