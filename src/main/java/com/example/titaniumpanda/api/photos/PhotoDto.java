package com.example.titaniumpanda.api.photos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PhotoDto {


    private final String title;
    private final String photoId;

    public PhotoDto(@JsonProperty("title") String title,
                    @JsonProperty("photoId") String photoId) {
        this.title = title;
        this.photoId = photoId;
    }

    public String getTitle() {
        return title;
    }

    public String getPhotoId() {
        return photoId;
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
