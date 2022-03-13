package com.titaniumpanda.app.api.photo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PhotoDto {

    private final UUID photoId;
    private final String title;
    private final String photoThumbnailUrl;
    private final String description;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime modifiedDateTime;
    private final String photoBaseUrl;
    private final List<UUID> categoryIds;

    public PhotoDto(@JsonProperty("photoId") UUID photoId,
                    @JsonProperty("title") String title,
                    @JsonProperty("photoThumbnailUrl") String photoThumbnailUrl,
                    @JsonProperty("description") String description,
                    @JsonProperty("createdDateTime") LocalDateTime createdDateTime,
                    @JsonProperty("modifiedDateTime") LocalDateTime modifiedDateTime,
                    @JsonProperty("photoBaseUrl") String photoBaseUrl,
                    @JsonProperty("categoryIds") List<UUID> categoryIds) {
        this.photoId = photoId;
        this.title = title;
        this.photoThumbnailUrl = photoThumbnailUrl;
        this.description = description;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.photoBaseUrl = photoBaseUrl;
        this.categoryIds = categoryIds;
    }

    public String getTitle() {
        return title;
    }

    public UUID getPhotoId() {
        return photoId;
    }

    @JsonIgnore
    public String getPhotoIdAsString() {
        return photoId.toString();
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoThumbnailUrl() {
        return photoThumbnailUrl;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public String getPhotoBaseUrl() {
        return photoBaseUrl;
    }

    public List<UUID> getCategoryIds() {
        return categoryIds;
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
