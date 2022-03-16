package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.api.category.CategoryRequest;
import com.titaniumpanda.app.api.category.CategoryUpdateRequest;

import java.time.LocalDateTime;

public class CategoryFactory {

    private final IdService idService;

    public CategoryFactory(IdService idService) {
        this.idService = idService;
    }

    public CategoryDto convertToDto(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getCategoryDescription(),
                category.getCreatedDateTime(),
                category.getModifiedDateTime());
    }


    public Category createNewCategory(CategoryRequest categoryRequest) {
        LocalDateTime createdDateTime = LocalDateTime.now();
        return new Category(
                idService.createNewId(),
                categoryRequest.getCategoryName(),
                categoryRequest.getCategoryDescription(),
                createdDateTime,
                createdDateTime);
    }

    public Category updateCategory(Category existingCategory, CategoryUpdateRequest categoryUpdateRequest) {
        LocalDateTime modifiedDateTime = LocalDateTime.now();
        String categoryNameUpdate = categoryUpdateRequest.getCategoryName();
        String categoryDescriptionUpdate = categoryUpdateRequest.getCategoryDescription();
        return new Category(
                existingCategory.getCategoryId(),
                categoryNameUpdate == null ? existingCategory.getCategoryName() : categoryNameUpdate,
                categoryDescriptionUpdate == null ? existingCategory.getCategoryDescription() : categoryDescriptionUpdate,
                existingCategory.getCreatedDateTime(),
                modifiedDateTime);
    }
}
