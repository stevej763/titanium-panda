package com.titaniumpanda.app.repository;

import com.titaniumpanda.app.domain.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PhotoRepository extends MongoRepository<Photo, UUID> {

}
