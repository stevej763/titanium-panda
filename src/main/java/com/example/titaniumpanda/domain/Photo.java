package com.example.titaniumpanda.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Photo {

    private final String title;
    private final String photoId;
    private final String photoThumbnailUrl;
    private final String photoDescription;

    public Photo(String title, String photoId, String photoThumbnailUrl, String photoDescription) {
        this.title = title;
        this.photoId = photoId;
        this.photoThumbnailUrl = photoThumbnailUrl;
        this.photoDescription = photoDescription;
    }

    public String getTitle() {
        return title;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getPhotoThumbnailUrl() {
        return photoThumbnailUrl;
    }

    public String getPhotoDescription() {
        return photoDescription;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
