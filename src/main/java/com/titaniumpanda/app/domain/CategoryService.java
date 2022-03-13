package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.api.category.CategoryRequest;
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

    public Optional<CategoryDto> findBy(UUID id) {
        return categoryRepository.findById(id).map(categoryFactory::convertToDto);
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryFactory::convertToDto).collect(toList());
    }

    public Optional<CategoryDto> save(CategoryRequest categoryRequest) {
        Category persistedCategory = categoryFactory.createNewCategory(categoryRequest);
        Category result = categoryRepository.save(persistedCategory);

        LOGGER.info("new category saved createdDateTime={} categoryId={} categoryName={}", result.getCreatedDateTime(), result.getCategoryId(), result.getCategoryName());
        return Optional.of(categoryFactory.convertToDto(result));
    }
}
