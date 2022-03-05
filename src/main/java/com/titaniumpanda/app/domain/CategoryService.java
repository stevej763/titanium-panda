package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryService {

    @Autowired
    CategoryDao categoryDao;
    CategoryFactory categoryFactory;

    public CategoryService(CategoryDao categoryDao, CategoryFactory categoryFactory) {
        this.categoryDao = categoryDao;
        this.categoryFactory = categoryFactory;
    }

    public Optional<CategoryDto> findBy(String id) {
        return categoryDao.findById(id).map(categoryFactory::convertToDto);
    }

    public List<CategoryDto> findAll() {
        return categoryDao.findAll().stream().map(categoryFactory::convertToDto).collect(Collectors.toList());
    }
}
