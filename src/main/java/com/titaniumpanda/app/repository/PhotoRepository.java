package com.titaniumpanda.app.repository;

import com.titaniumpanda.app.domain.Photo;
import com.titaniumpanda.app.domain.ids.PhotoId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, PhotoId> {

}
