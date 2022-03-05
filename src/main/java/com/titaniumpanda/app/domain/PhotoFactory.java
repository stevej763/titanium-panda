package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.photo.PhotoDto;

public class PhotoFactory {
    public PhotoDto convertToDto(Photo photo) {
        return new PhotoDto(photo.getTitle(), photo.getPhotoId(), photo.getPhotoThumbnailUrl(), photo.getPhotoDescription());
    }
}
