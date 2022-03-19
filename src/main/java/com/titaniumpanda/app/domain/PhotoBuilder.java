package com.titaniumpanda.app.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PhotoBuilder {

    private UUID photoId;
    private String title;
    private UUID uploadId;
    private String photoDescription;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
    private List<UUID> categories;

    public PhotoBuilder setPhotoId(UUID photoId) {
        this.photoId = photoId;
        return this;
    }

    public PhotoBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public PhotoBuilder setUploadId(UUID uploadId) {
        this.uploadId = uploadId;
        return this;
    }

    public PhotoBuilder setPhotoDescription(String description) {
        this.photoDescription = description;
        return this;
    }

    public PhotoBuilder setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;

    }public PhotoBuilder setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public PhotoBuilder setCategories(List<UUID> categories) {
        this.categories = categories;
        return this;
    }

    public Photo build() {
        LocalDateTime modifiedTimeToSave = modifiedDateTime == null ? createdDateTime : modifiedDateTime;
        return new Photo(photoId, title, uploadId, photoDescription, createdDateTime, modifiedTimeToSave, categories);
    }
}
