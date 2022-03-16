package com.titaniumpanda.app.repository;

import com.titaniumpanda.app.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends MongoRepository<Category, UUID> {

    Optional<Category> findByCategoryName(String title);
}
