package com.titaniumpanda.app.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PhotoBuilder {

    private UUID photoId;
    private String title;
    private String photoThumbnailUrl;
    private String photoDescription;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
    private String photoBaseUrl;
    private List<UUID> categories;

    public PhotoBuilder setPhotoId(UUID photoId) {
        this.photoId = photoId;
        return this;
    }

    public PhotoBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public PhotoBuilder setPhotoThumbnail(String photoThumbnailUrl) {
        this.photoThumbnailUrl = photoThumbnailUrl;
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

    public PhotoBuilder setPhotoBaseUrl(String photoBaseUrl) {
        this.photoBaseUrl = photoBaseUrl;
        return this;
    }

    public PhotoBuilder setCategories(List<UUID> categories) {
        this.categories = categories;
        return this;
    }

    public Photo build() {
        LocalDateTime modifiedTimeToSave = modifiedDateTime == null ? createdDateTime : modifiedDateTime;
        return new Photo(photoId, title, photoThumbnailUrl, photoDescription, createdDateTime, modifiedTimeToSave, photoBaseUrl, categories);
    }
}
