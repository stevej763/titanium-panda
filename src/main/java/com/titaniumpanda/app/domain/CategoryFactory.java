package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;

public class CategoryFactory {
    public CategoryDto convertToDto(Category category) {
        return new CategoryDto(category.getCategoryId(), category.getCategoryName(), category.getCategoryThumbnailUrl(), category.getCategoryDescription());
    }
}
