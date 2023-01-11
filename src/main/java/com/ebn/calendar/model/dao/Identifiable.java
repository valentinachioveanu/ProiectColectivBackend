package com.ebn.calendar.model.dao;

import java.io.Serializable;
import java.util.Objects;

public abstract class Identifiable<ID> implements Serializable {

    abstract public ID getIdentifier();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifiable identifiable = (Identifiable) o;
        return Objects.equals(getIdentifier(), identifiable.getIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier());
    }
}
