package com.titaniumpanda.app.dao;

import com.titaniumpanda.app.domain.Category;

import java.util.List;
import java.util.Optional;

public class CategoryDao {

    public final List<Category> fakeDb = List.of(
            new Category("1", "title", "thumbnailUrl", "description"),
            new Category("2", "title", "thumbnailUrl", "description"),
            new Category("3", "title", "thumbnailUrl", "description"));

    public List<Category> findAll() {
        return fakeDb;
    }

    public Optional<Category> findById(String id) {
        return fakeDb.stream().filter(category -> category.getCategoryId().equals(id)).findFirst();
    }
}
