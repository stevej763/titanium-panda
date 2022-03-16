package com.titaniumpanda.app.api.category;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
