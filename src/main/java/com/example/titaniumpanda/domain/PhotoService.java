package com.example.titaniumpanda.domain;

import com.example.titaniumpanda.api.photos.PhotoDto;
import com.example.titaniumpanda.dao.PhotoDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PhotoService {

    @Autowired
    private final PhotoFactory photoFactory;
    private final PhotoDao photoDao;

    public PhotoService(PhotoFactory photoFactory, PhotoDao photoDao) {
        this.photoFactory = photoFactory;
        this.photoDao = photoDao;
    }

    public Optional<PhotoDto> findPhotoBy(String id) {
       return photoDao.findById(id).map(photoFactory::convertToDto);
    }

    public Set<PhotoDto> findAllPhotos() {
        return photoDao.findAll().stream().map(photoFactory::convertToDto).collect(Collectors.toSet());
    }
}
