package com.titaniumpanda.app.api.external;

import java.util.List;
import java.util.UUID;

public class PhotoUploadBatch {

    private final UUID uploadId;
    private final List<PhotoUploadWrapper> photoUploadWrappers;
    private final String fileFormat;

    public PhotoUploadBatch(UUID uploadId, List<PhotoUploadWrapper> photoUploadWrappers, String fileFormat) {
        this.uploadId = uploadId;
        this.photoUploadWrappers = photoUploadWrappers;
        this.fileFormat = fileFormat;
    }

    public UUID getUploadId() {
        return uploadId;
    }

    public List<PhotoUploadWrapper> getPhotoUploadWrappers() {
        return photoUploadWrappers;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public String getFileKey() {
        return uploadId + "." + fileFormat;
    }
}
