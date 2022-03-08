package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PhotoService {

    @Autowired
    private final PhotoFactory photoFactory;
    private final PhotoRepository photoRepository;

    public PhotoService(PhotoFactory photoFactory, PhotoRepository photoRepository) {
        this.photoFactory = photoFactory;
        this.photoRepository = photoRepository;
    }

    public Optional<PhotoDto> findPhotoBy(String id) {
       return photoRepository.findByPhotoId(id).map(photoFactory::convertToDto);
    }

    public List<PhotoDto> findAll() {
        return photoRepository.findAll().stream().map(photoFactory::convertToDto).collect(Collectors.toList());
    }
}
