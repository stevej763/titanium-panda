package com.titaniumpanda.app.repository;

import com.titaniumpanda.app.domain.Photo;
import com.titaniumpanda.app.domain.ids.PhotoId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PhotoRepository extends MongoRepository<Photo, PhotoId> {

    Optional<Photo> findByPhotoId(PhotoId photoId);
}
