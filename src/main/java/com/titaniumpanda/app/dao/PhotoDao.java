package com.titaniumpanda.app.dao;

import com.titaniumpanda.app.domain.Photo;

import java.util.List;
import java.util.Optional;

public class PhotoDao {

    private final List<Photo> fakeDb = List.of(
            new Photo("photo title", "1", "photoUrl", "description"),
            new Photo("photo title", "2", "photoUrl", "description"),
            new Photo("photo title", "3", "photoUrl", "description"));

    public List<Photo> findAll() {
        return fakeDb;
    }

    public Optional<Photo> findById(String id) {
        return fakeDb.stream().filter(photo -> photo.getPhotoId().equals(id)).findFirst();
    }
}
