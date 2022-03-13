package com.titaniumpanda.app.repository;

import com.titaniumpanda.app.domain.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PhotoRepository extends MongoRepository<Photo, UUID> {

    @Query("{ 'categoryIds' : ?0 }")
    List<Photo> findByCategoryId(UUID categoryId);
}
