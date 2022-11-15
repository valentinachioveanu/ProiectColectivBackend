package com.ebn.calendar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Identifiable<ID> {

    @JsonIgnore
    ID getIdentifier();
}
