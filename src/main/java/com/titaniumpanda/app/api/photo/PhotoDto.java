package com.titaniumpanda.app.api.photo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.titaniumpanda.app.domain.ids.CategoryId;
import com.titaniumpanda.app.domain.ids.PhotoId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.List;

public class PhotoDto {

    private final PhotoId photoId;
    private final String title;
    private final String photoThumbnailUrl;
    private final String description;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime modifiedDateTime;
    private final String photoBaseUrl;
    private final List<CategoryId> categoryIds;

    public PhotoDto(@JsonProperty("photoId") PhotoId photoId,
                    @JsonProperty("title") String title,
                    @JsonProperty("photoThumbnailUrl") String photoThumbnailUrl,
                    @JsonProperty("description") String description,
                    @JsonProperty("createdDateTime") LocalDateTime createdDateTime,
                    @JsonProperty("modifiedDateTime") LocalDateTime modifiedDateTime,
                    @JsonProperty("photoBaseUrl") String photoBaseUrl,
                    @JsonProperty("categoryIds") List<CategoryId> categoryIds) {
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

    public PhotoId getPhotoId() {
        return photoId;
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

    public List<CategoryId> getCategoryIds() {
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
