package com.example.titaniumpanda.domain;

import com.example.titaniumpanda.api.photos.PhotoDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PhotoService {

    private final List<PhotoDto> fakeDb = List.of(
            new PhotoDto("photo title", "1"),
            new PhotoDto("photo title", "2"),
            new PhotoDto("photo title", "3"),
            new PhotoDto("photo title", "4"),
            new PhotoDto("photo title", "5")
    );

    public Optional<PhotoDto> findPhotoBy(String id) {
       return fakeDb.stream().filter(photoDto -> photoDto.getPhotoId().equals(id)).findFirst();
    }
}
