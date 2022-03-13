package com.titaniumpanda.app.api.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

public class CategoryDto {

    private final UUID categoryId;
    private final String categoryName;
    private final String categoryDescription;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime modifiedDateTime;

    public CategoryDto(@JsonProperty("categoryId") UUID categoryId,
                       @JsonProperty("categoryName") String categoryName,
                       @JsonProperty("categoryDescription") String categoryDescription,
                       @JsonProperty("createdDateTime")LocalDateTime createdDateTime,
                       @JsonProperty("modifiedDateTime")LocalDateTime modifiedDateTime) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }
}
