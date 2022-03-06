package com.titaniumpanda.app.repository;

import com.titaniumpanda.app.domain.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PhotoRepository extends MongoRepository<Photo, String> {

    Optional<Photo> findByPhotoId(String photoId);
}
