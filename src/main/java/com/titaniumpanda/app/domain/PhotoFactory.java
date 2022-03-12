package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.api.photo.PhotoRequestMetadata;

import java.time.LocalDateTime;

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
        return new Photo(idService.getNewPhotoId(),
                photoRequestMetadata.getTitle(),
                photoUploadDetails.getThumbnailUrl(),
                photoRequestMetadata.getDescription(),
                createdDateTime,
                createdDateTime,
                photoUploadDetails.getPhotoUrl(),
                photoRequestMetadata.getCategoryIds());
    }
}
