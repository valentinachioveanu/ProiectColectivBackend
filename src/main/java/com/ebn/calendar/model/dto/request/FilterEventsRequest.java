package com.ebn.calendar.model.dto.request;

import java.util.List;

public class FilterEventsRequest {
    private List<String> tagsIds;

    public List<String> getTagsIds() {
        return tagsIds;
    }

    public void setTagsIds(List<String> tagsIds) {
        this.tagsIds = tagsIds;
    }
}
