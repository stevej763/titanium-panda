package com.example.titaniumpanda.domain;

import com.example.titaniumpanda.api.photos.PhotoDto;

public class PhotoFactory {
    public PhotoDto convertToDto(Photo photo) {
        return new PhotoDto(photo.getTitle(), photo.getPhotoId(), photo.getPhotoThumbnailUrl(), photo.getPhotoDescription());
    }
}
