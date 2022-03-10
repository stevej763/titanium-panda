package com.titaniumpanda.app.domain.ids;

import java.util.UUID;

public abstract class Id {

    private UUID id;

    public Id() {
        this.id = UUID.randomUUID();
    }

    public Id(UUID id) {
        this.id = id;
    }

    public Id(String id) {
        this.id = UUID.fromString(id);
    }

    public UUID getId() {
        return id;
    }
}
