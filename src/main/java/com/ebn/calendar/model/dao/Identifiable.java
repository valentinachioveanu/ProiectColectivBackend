package com.ebn.calendar.model.dao;

import java.io.Serializable;

public interface Identifiable<ID> extends Serializable {

    ID getIdentifier();
}
