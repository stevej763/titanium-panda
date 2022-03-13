package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.api.category.CategoryRequest;

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
}
