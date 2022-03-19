package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.external.PhotoUploadDetail;
import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.api.photo.PhotoRequestMetadata;
import com.titaniumpanda.app.api.photo.PhotoUpdateRequest;

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
                photo.getUploadId(),
                photo.getDescription(),
                photo.getCreatedDateTime(),
                photo.getModifiedDateTime(),
                photo.getCategoryIds());
    }

    public Photo createNewPhoto(PhotoUploadDetail photoUploadDetail, PhotoRequestMetadata photoRequestMetadata) {
        LocalDateTime createdDateTime = LocalDateTime.now();
        UUID newPhotoId = idService.createNewId();
        return new Photo(newPhotoId,
                photoRequestMetadata.getTitle(),
                photoUploadDetail.getUploadId(),
                photoRequestMetadata.getDescription(),
                createdDateTime,
                createdDateTime,
                photoRequestMetadata.getCategoryIds());
    }

    public Photo updatePhotoWithNewCategory(Photo photo, UUID categoryId) {
        LocalDateTime modifiedDateTime = LocalDateTime.now();
        List<UUID> categoryIds = new ArrayList<>(photo.getCategoryIds());
        categoryIds.add(categoryId);
        return new Photo(photo.getPhotoId(),
                photo.getPhotoTitle(),
                photo.getUploadId(),
                photo.getDescription(),
                photo.getCreatedDateTime(),
                modifiedDateTime,
                categoryIds);
    }

    public Photo updatePhotoWithCategoryRemoved(Photo photo, UUID categoryId) {
        List<UUID> categoryIds = new ArrayList<>(photo.getCategoryIds());
        categoryIds.remove(categoryId);
        LocalDateTime modifiedDateTime = LocalDateTime.now();
        return new Photo(photo.getPhotoId(),
                photo.getPhotoTitle(),
                photo.getUploadId(),
                photo.getDescription(),
                photo.getCreatedDateTime(),
                modifiedDateTime,
                categoryIds);
    }

    public Photo updatePhoto(Photo photo, PhotoUpdateRequest photoUpdateRequest) {
        LocalDateTime modifiedDateTime = LocalDateTime.now();
        String updatedPhotoTitle = photoUpdateRequest.getPhotoTitle();
        String updatedPhotoDescription = photoUpdateRequest.getPhotoDescription();
        return new Photo(photo.getPhotoId(),
                updatedPhotoTitle == null ? photo.getPhotoTitle() : updatedPhotoTitle,
                photo.getUploadId(),
                updatedPhotoDescription == null ? photo.getPhotoTitle() : updatedPhotoDescription,
                photo.getCreatedDateTime(),
                modifiedDateTime,
                photo.getCategoryIds());
    }
}
