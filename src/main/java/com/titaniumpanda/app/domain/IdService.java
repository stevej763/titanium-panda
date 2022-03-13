package com.titaniumpanda.app.domain;

import java.util.UUID;

public class IdService {

    public IdService() {
    }

    public UUID createNewId() {
        return UUID.randomUUID();
    }
}
