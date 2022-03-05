package com.example.titaniumpanda.dao;

import com.example.titaniumpanda.api.photos.PhotoDto;
import com.example.titaniumpanda.domain.Photo;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class PhotoDao {

    private final Set<Photo> fakeDb = Set.of(
            new Photo("photo title", "1", "photoUrl", "description"),
            new Photo("photo title", "2", "photoUrl", "description"),
            new Photo("photo title", "3", "photoUrl", "description"));

    public Set<Photo> findAll() {
        return fakeDb;
    }

    public Optional<Photo> findById(String id) {
        return fakeDb.stream().filter(photo -> photo.getPhotoId().equals(id)).findFirst();
    }
}
