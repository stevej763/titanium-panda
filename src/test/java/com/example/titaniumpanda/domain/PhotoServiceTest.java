package com.example.titaniumpanda.domain;

import com.example.titaniumpanda.api.photos.PhotoDto;
import com.example.titaniumpanda.dao.PhotoDao;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoServiceTest {

    private final PhotoFactory photoFactory = mock(PhotoFactory.class);
    private final PhotoDao photoDao = mock(PhotoDao.class);

    private final PhotoService underTest = new PhotoService(photoFactory, photoDao);
    private final String photoId = "100";

    @Test
    public void shouldReturnPhotoDto() {
        PhotoDto photoDto = new PhotoDto("photo title", photoId, "photoUrl", "description");
        Photo photo = new Photo("title", "id", "photoUrl", "description");
        when(photoDao.findById(photoId)).thenReturn(Optional.of(photo));
        when(photoFactory.convertToDto(photo)).thenReturn(photoDto);
        assertThat(underTest.findPhotoBy(photoId), is(Optional.of(photoDto)));
    }

    @Test
    public void shouldReturnOptionalEmptyIfIdNotFound() {
        when(photoDao.findById(photoId)).thenReturn(Optional.empty());
        assertThat(underTest.findPhotoBy(photoId), is(Optional.empty()));
    }

    @Test
    public void shouldReturnSetOfPhotoDtoObjects() {
        Photo photo1 = new Photo("photo title", "1", "photoUrl", "description");
        Photo photo2 = new Photo("photo title", "2", "photoUrl", "description");
        Photo photo3 = new Photo("photo title", "3", "photoUrl", "description");
        Set<Photo> photos = Set.of(
                photo1,
                photo2,
                photo3
        );
        PhotoDto photoDto1 = new PhotoDto("photo title", "1", "photoUrl", "description");
        PhotoDto photoDto2 = new PhotoDto("photo title", "2", "photoUrl", "description");
        PhotoDto photoDto3 = new PhotoDto("photo title", "3", "photoUrl", "description");
        Set<PhotoDto> photoDtos = Set.of(
                photoDto1,
                photoDto2,
                photoDto3
        );

        when(photoDao.findAll()).thenReturn(photos);
        when(photoFactory.convertToDto(photo1)).thenReturn(photoDto1);
        when(photoFactory.convertToDto(photo2)).thenReturn(photoDto2);
        when(photoFactory.convertToDto(photo3)).thenReturn(photoDto3);

        assertThat(underTest.findAllPhotos(), is(photoDtos));
    }

    @Test
    public void shouldReturnEmptySetIfNoPhotosFound() {
        assertThat(underTest.findAllPhotos(), is(emptySet()));
    }
}