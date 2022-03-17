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
    private final PhotoUploadResource photoUploadResource;
    @Autowired
    private final CategoryService categoryService;

    public PhotoService(PhotoFactory photoFactory,
                        PhotoRepository photoRepository,
                        PhotoUploadResource photoUploadResource,
                        CategoryService categoryService) {
        this.photoFactory = photoFactory;
        this.photoRepository = photoRepository;
        this.photoUploadResource = photoUploadResource;
        this.categoryService = categoryService;
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
            LOGGER.info("photo saved photoId={} photoTitle={}", result.getPhotoId(), result.getPhotoTitle());
            return Optional.of(photoFactory.convertToDto(result));
        } else {
            LOGGER.error("Error Uploading photo");
            return Optional.empty();
        }
    }

    public List<PhotoDto> findByCategoryId(UUID categoryId) {
        return photoRepository.findByCategoryId(categoryId).stream().map(photoFactory::convertToDto).collect(Collectors.toList());
    }

    public Boolean deletePhoto(UUID photoId) {
        Optional<Photo> photo = photoRepository.findById(photoId);
        if (photo.isPresent()) {
            //todo add in logic to delete photo in s3 too
            Photo photoToDelete = photo.get();
            photoRepository.delete(photoToDelete);
            LOGGER.info("photo deleted photoId={} photoTitle={}",
                    photoToDelete.getPhotoId(), photoToDelete.getPhotoTitle());
            return true;
        } else {
            return false;
        }
    }

    public Optional<PhotoDto> addPhotoToCategory(UUID photoId, UUID categoryId) {
        Optional<Photo> result = photoRepository.findById(photoId);
        if (result.isPresent()) {
            Photo photo = result.get();
            if(categoryExists(categoryId, photoId) && !alreadyContainsCategoryId(categoryId, photo)) {
                Photo modifiedPhoto = photoFactory.updatePhotoWithNewCategory(photo, categoryId);
                Photo updatedPhoto = photoRepository.save(modifiedPhoto);
                return Optional.of(photoFactory.convertToDto(updatedPhoto));
            } else {
                return Optional.empty();
            }
        } else {
            LOGGER.info("no photo found with photoId={}", photoId);
            return Optional.empty();
        }
    }

    private boolean categoryExists(UUID categoryId, UUID photoId) {
        boolean category = categoryService.findById(categoryId).isPresent();
        if(!category) {
            LOGGER.info("category does not exist to add to photo={} categoryId={}", photoId, categoryId);
        }
        return category;
    }

    private boolean alreadyContainsCategoryId(UUID categoryId, Photo photo) {
        boolean result = photo.getCategoryIds().contains(categoryId);
        if(result) {
            LOGGER.info("photo already in category photoId={} categoryId={}", photo.getPhotoId(), categoryId);
        }
        return result;
    }
}
