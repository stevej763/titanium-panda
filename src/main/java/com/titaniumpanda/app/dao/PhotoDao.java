package com.titaniumpanda.app.dao;

import com.titaniumpanda.app.domain.Photo;
import com.titaniumpanda.app.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PhotoDao {

    @Autowired
    PhotoRepository photoRepository;

    public PhotoDao(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<Photo> findAll() {
        System.out.println("in photo DAO");
        return photoRepository.findAll();
    }

    public Optional<Photo> findById(String id) {
        System.out.println("in photo DAO");
        return photoRepository.findByPhotoId(id);
    }
}
