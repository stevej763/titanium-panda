package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.api.photo.PhotoRequestMetadata;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PhotoFactory {

    private final IdService idService;

    public PhotoFactory(IdService idService) {
        this.idService = idService;
    }

    public PhotoDto convertToDto(Photo photo) {
        return new PhotoDto(photo.getPhotoId(),
                photo.getPhotoTitle(),
                photo.getPhotoThumbnailUrl(),
                photo.getDescription(),
                photo.getCreatedDateTime(),
                photo.getModifiedDateTime(),
                photo.getPhotoBaseUrl(),
                photo.getCategoryIds());
    }

    public Photo createNewPhoto(PhotoUploadDetails photoUploadDetails, PhotoRequestMetadata photoRequestMetadata) {
        LocalDateTime createdDateTime = LocalDateTime.now();
        UUID newPhotoId = idService.createNewId();
        return new Photo(newPhotoId,
                photoRequestMetadata.getTitle(),
                photoUploadDetails.getThumbnailUrl(),
                photoRequestMetadata.getDescription(),
                createdDateTime,
                createdDateTime,
                photoUploadDetails.getPhotoUrl(),
                photoRequestMetadata.getCategoryIds());
    }

    public Photo updatePhotoWithNewCategory(Photo photo, UUID categoryId) {
        LocalDateTime modifiedDateTime = LocalDateTime.now();
        List<UUID> categoryIds = new ArrayList<>(photo.getCategoryIds());
        categoryIds.add(categoryId);
        return new Photo(photo.getPhotoId(),
                photo.getPhotoTitle(),
                photo.getPhotoThumbnailUrl(),
                photo.getDescription(),
                photo.getCreatedDateTime(),
                modifiedDateTime,
                photo.getPhotoBaseUrl(),
                categoryIds);
    }

    public Photo updatePhotoWithCategoryRemoved(Photo photo, UUID categoryId) {
        List<UUID> categoryIds = new ArrayList<>(photo.getCategoryIds());
        categoryIds.remove(categoryId);
        LocalDateTime modifiedDateTime = LocalDateTime.now();
        return new Photo(photo.getPhotoId(),
                photo.getPhotoTitle(),
                photo.getPhotoThumbnailUrl(),
                photo.getDescription(),
                photo.getCreatedDateTime(),
                modifiedDateTime,
                photo.getPhotoBaseUrl(),
                categoryIds);
    }
}
