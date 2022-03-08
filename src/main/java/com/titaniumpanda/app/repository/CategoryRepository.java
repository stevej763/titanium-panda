package com.titaniumpanda.app.repository;

import com.titaniumpanda.app.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findByCategoryId(String categoryId);
}
