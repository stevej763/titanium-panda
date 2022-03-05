package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.dao.PhotoDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
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

    public List<PhotoDto> findAll() {
        return photoDao.findAll().stream().map(photoFactory::convertToDto).collect(Collectors.toList());
    }
}
