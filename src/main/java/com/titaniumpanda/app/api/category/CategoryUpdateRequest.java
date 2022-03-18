package com.titaniumpanda.app.api.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CategoryUpdateRequest {

    private final String categoryId;
    private final String categoryName;
    private final String categoryDescription;

    public CategoryUpdateRequest(@JsonProperty("categoryId") String categoryId,
                                 @JsonProperty("categoryName") String categoryName,
                                 @JsonProperty("categoryDescription") String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryId() {
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
}
