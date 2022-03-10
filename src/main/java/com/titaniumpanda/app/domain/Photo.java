package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.domain.ids.CategoryId;
import com.titaniumpanda.app.domain.ids.PhotoId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class Photo {

    @Id
    private final PhotoId photoId;

    private final String title;
    private final String photoThumbnailUrl;
    private final String photoDescription;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime modifiedDateTime;
    private final String photoBaseUrl;
    private final List<CategoryId> categories;

    public Photo(PhotoId photoId,
                 String title,
                 String photoThumbnailUrl,
                 String photoDescription,
                 LocalDateTime createdDateTime,
                 LocalDateTime modifiedDateTime,
                 String photoBaseUrl,
                 List<CategoryId> categories) {
        this.photoId = photoId;
        this.title = title;
        this.photoThumbnailUrl = photoThumbnailUrl;
        this.photoDescription = photoDescription;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.photoBaseUrl = photoBaseUrl;
        this.categories = categories;
    }

    public PhotoId getPhotoId() {
        return photoId;
    }

    public String getTitle() {
        return title;
    }

    public String getPhotoThumbnailUrl() {
        return photoThumbnailUrl;
    }

    public String getPhotoDescription() {
        return photoDescription;
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

    public List<CategoryId> getCategories() {
        return categories;
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
