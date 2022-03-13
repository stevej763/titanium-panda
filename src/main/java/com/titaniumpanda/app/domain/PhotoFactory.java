package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.api.photo.PhotoRequestMetadata;

import java.time.LocalDateTime;
import java.util.UUID;

public class PhotoFactory {

    private IdService idService;

    public PhotoFactory(IdService idService) {
        this.idService = idService;
    }

    public PhotoDto convertToDto(Photo photo) {
        return new PhotoDto(photo.getPhotoId(),
                photo.getTitle(),
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
}
