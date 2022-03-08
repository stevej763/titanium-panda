package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    CategoryFactory categoryFactory;

    public CategoryService(CategoryRepository categoryRepository, CategoryFactory categoryFactory) {
        this.categoryRepository = categoryRepository;
        this.categoryFactory = categoryFactory;
    }

    public Optional<CategoryDto> findBy(String id) {
        return categoryRepository.findByCategoryId(id).map(categoryFactory::convertToDto);
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryFactory::convertToDto).collect(toList());
    }
}
