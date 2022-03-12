package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.domain.ids.PhotoId;
import com.titaniumpanda.app.domain.ids.S3UploadId;

public class IdService {

    public IdService() {
    }

    public PhotoId getNewPhotoId() {
        return new PhotoId();
    }

    public PhotoId getNewS3UploadId() {
        return new S3UploadId();
    }
}
