package com.titaniumpanda.app.domain.ids;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

public abstract class AbstractId {

    private UUID id;

    public AbstractId() {
        this.id = UUID.randomUUID();
    }

    public AbstractId(UUID id) {
        this.id = id;
    }

    public AbstractId(String id) {
        this.id = UUID.fromString(id);
    }

    public UUID getId() {
        return id;
    }


    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return this.id.toString();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
