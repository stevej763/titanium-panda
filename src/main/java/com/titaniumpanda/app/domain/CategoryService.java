package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.api.category.CategoryRequest;
import com.titaniumpanda.app.api.category.CategoryUpdateRequest;
import com.titaniumpanda.app.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class CategoryService {

    Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    CategoryRepository categoryRepository;
    CategoryFactory categoryFactory;

    public CategoryService(CategoryRepository categoryRepository, CategoryFactory categoryFactory) {
        this.categoryRepository = categoryRepository;
        this.categoryFactory = categoryFactory;
    }

    public Optional<CategoryDto> findById(UUID id) {
        return categoryRepository.findById(id).map(categoryFactory::convertToDto);
    }

    public Optional<CategoryDto> findByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).map(categoryFactory::convertToDto);
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryFactory::convertToDto).collect(toList());
    }

    public Optional<CategoryDto> save(CategoryRequest categoryRequest) {
        Category persistedCategory = categoryFactory.createNewCategory(categoryRequest);
        Category result = categoryRepository.save(persistedCategory);

        LOGGER.info("category created createdDateTime={} categoryId={} categoryName={}",
                result.getCreatedDateTime(), result.getCategoryId(), result.getCategoryName());
        return Optional.of(categoryFactory.convertToDto(result));
    }

    public Optional<CategoryDto> update(CategoryUpdateRequest categoryUpdateRequest) {
        UUID categoryId = UUID.fromString(categoryUpdateRequest.getCategoryId());
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if(existingCategory.isPresent()) {
            Category updatedCategory = categoryFactory.updateCategory(existingCategory.get(), categoryUpdateRequest);
            Category savedCategory = categoryRepository.save(updatedCategory);
            LOGGER.info("category updated updatedTime={} categoryId={} categoryName={}",
                    savedCategory.getModifiedDateTime(), savedCategory.getCategoryId(), savedCategory.getCategoryName());
            return Optional.of(categoryFactory.convertToDto(savedCategory));
        } else {
            return Optional.empty();
        }
    }

    public Boolean deleteCategory(UUID categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Category categoryToDelete = category.get();
            categoryRepository.delete(categoryToDelete);
            LOGGER.info("category deleted categoryId={} categoryName={}",
                    categoryToDelete.getCategoryId(), categoryToDelete.getCategoryName());
            return true;
        } else {
            return false;
        }
    }
}
