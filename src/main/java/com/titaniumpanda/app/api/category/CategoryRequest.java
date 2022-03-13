package com.titaniumpanda.app.api.category;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryRequest {


    private final String categoryName;
    private final String categoryDescription;

    public CategoryRequest(@JsonProperty("categoryName") String categoryName,
                           @JsonProperty("categoryDescription") String categoryDescription) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }
}
