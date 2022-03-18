package com.titaniumpanda.app.api.photo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

public class PhotoUpdateRequest {
    private final UUID photoId;
    private final String photoTitle;
    private final String photoDescription;

    public PhotoUpdateRequest(@JsonProperty("photoId") UUID photoId,
                              @JsonProperty("photoTitle") String photoTitle,
                              @JsonProperty("photoDescription") String photoDescription) {
        this.photoId = photoId;
        this.photoTitle = photoTitle;
        this.photoDescription = photoDescription;
    }

    public UUID getPhotoId() {
        return photoId;
    }

    public String getPhotoTitle() {
        return photoTitle;
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
