package com.titaniumpanda.app.domain.ids;

import java.util.UUID;

public class S3UploadId extends AbstractId {

    public S3UploadId() {
    }

    public S3UploadId(UUID id) {
        super(id);
    }

    public S3UploadId(String id) {
        super(id);
    }
}
