package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.external.PhotoUploadResource;
import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.api.photo.PhotoRequestMetadata;
import com.titaniumpanda.app.repository.PhotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PhotoService {

    Logger LOGGER = LoggerFactory.getLogger(PhotoService.class);

    @Autowired
    private final PhotoFactory photoFactory;
    @Autowired
    private final PhotoRepository photoRepository;
    @Autowired
    private PhotoUploadResource photoUploadResource;

    public PhotoService(PhotoFactory photoFactory,
                        PhotoRepository photoRepository,
                        PhotoUploadResource photoUploadResource) {
        this.photoFactory = photoFactory;
        this.photoRepository = photoRepository;
        this.photoUploadResource = photoUploadResource;
    }

    public Optional<PhotoDto> findPhotoBy(UUID id) {
       return photoRepository.findById(id).map(photoFactory::convertToDto);
    }

    public List<PhotoDto> findAll() {
        return photoRepository.findAll().stream().map(photoFactory::convertToDto).collect(Collectors.toList());
    }

    public Optional<PhotoDto> save(MultipartFile photoFile, PhotoRequestMetadata photoRequestMetadata) {
        Optional<PhotoUploadDetails> optionalPhotoUploadDetails = photoUploadResource.upload(photoFile);
        if (optionalPhotoUploadDetails.isPresent()) {
            PhotoUploadDetails photoUploadDetails = optionalPhotoUploadDetails.get();
            Photo persistedPhoto = photoFactory.createNewPhoto(photoUploadDetails, photoRequestMetadata);
            Photo result = photoRepository.save(persistedPhoto);

            LOGGER.info("new photo saved photoId={} photoTitle={}", result.getPhotoId(), result.getTitle());
            return Optional.of(photoFactory.convertToDto(result));
        } else {
            LOGGER.error("Error Uploading photo");
            return Optional.empty();
        }
    }

    public List<PhotoDto> findByCategoryId(UUID categoryId) {
        return photoRepository.findByCategoryId(categoryId).stream().map(photoFactory::convertToDto).collect(Collectors.toList());
    }
}
