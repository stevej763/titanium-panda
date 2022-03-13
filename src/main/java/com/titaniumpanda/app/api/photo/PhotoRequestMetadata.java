package com.titaniumpanda.app.api.photo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.UUID;

public class PhotoRequestMetadata {

    private final String title;
    private final String description;
    private final List<UUID> categoryIds;

    public PhotoRequestMetadata(@JsonProperty("title") String title,
                                @JsonProperty("photoDescription") String description,
                                @JsonProperty("categoryIds") List<UUID> categoryIds) {
        this.title = title;
        this.description = description;
        this.categoryIds = categoryIds;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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
