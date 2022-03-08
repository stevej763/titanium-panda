package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.repository.PhotoRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoServiceTest {

    private final PhotoFactory photoFactory = mock(PhotoFactory.class);
    private final PhotoRepository photoRepository = mock(PhotoRepository.class);

    private final PhotoService underTest = new PhotoService(photoFactory, photoRepository);
    private final String photoId = "100";

    @Test
    public void shouldReturnPhotoDto() {
        PhotoDto photoDto = new PhotoDto("photo title", photoId, "photoUrl", "description");
        Photo photo = new Photo("title", "id", "photoUrl", "description");
        when(photoRepository.findByPhotoId(photoId)).thenReturn(Optional.of(photo));
        when(photoFactory.convertToDto(photo)).thenReturn(photoDto);
        assertThat(underTest.findPhotoBy(photoId), is(Optional.of(photoDto)));
    }

    @Test
    public void shouldReturnOptionalEmptyIfIdNotFound() {
        when(photoRepository.findByPhotoId(photoId)).thenReturn(Optional.empty());
        assertThat(underTest.findPhotoBy(photoId), is(Optional.empty()));
    }

    @Test
    public void shouldReturnSetOfPhotoDtoObjects() {
        Photo photo1 = new Photo("photo title", "1", "photoUrl", "description");
        Photo photo2 = new Photo("photo title", "2", "photoUrl", "description");
        Photo photo3 = new Photo("photo title", "3", "photoUrl", "description");
        List<Photo> photos = List.of(
                photo1,
                photo2,
                photo3
        );
        PhotoDto photoDto1 = new PhotoDto("photo title", "1", "photoUrl", "description");
        PhotoDto photoDto2 = new PhotoDto("photo title", "2", "photoUrl", "description");
        PhotoDto photoDto3 = new PhotoDto("photo title", "3", "photoUrl", "description");
        List<PhotoDto> photoDtos = List.of(
                photoDto1,
                photoDto2,
                photoDto3
        );

        when(photoRepository.findAll()).thenReturn(photos);
        when(photoFactory.convertToDto(photo1)).thenReturn(photoDto1);
        when(photoFactory.convertToDto(photo2)).thenReturn(photoDto2);
        when(photoFactory.convertToDto(photo3)).thenReturn(photoDto3);

        assertThat(underTest.findAll(), is(photoDtos));
    }

    @Test
    public void shouldReturnEmptyListIfNoPhotosFound() {
        assertThat(underTest.findAll(), is(emptyList()));
    }
}