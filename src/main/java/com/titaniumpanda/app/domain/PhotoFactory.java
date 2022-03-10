package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.photo.PhotoDto;

public class PhotoFactory {
    public PhotoDto convertToDto(Photo photo) {
        return new PhotoDto(photo.getPhotoId(),
                photo.getTitle(),
                photo.getPhotoThumbnailUrl(),
                photo.getPhotoDescription(),
                photo.getCreatedDateTime(),
                photo.getModifiedDateTime(),
                photo.getPhotoBaseUrl(),
                photo.getCategories());
    }
}
