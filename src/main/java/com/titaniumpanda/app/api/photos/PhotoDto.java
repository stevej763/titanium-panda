package com.titaniumpanda.app.api.photos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PhotoDto {

    private final String title;
    private final String photoId;
    private String photoUrl;
    private String description;

    public PhotoDto(@JsonProperty("title") String title,
                    @JsonProperty("photoId") String photoId,
                    @JsonProperty("photoUrl") String photoUrl,
                    @JsonProperty("description") String description) {
        this.title = title;
        this.photoId = photoId;
        this.photoUrl = photoUrl;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getDescription() {
        return description;
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
