package com.titaniumpanda.app.api.photo;

import com.titaniumpanda.app.domain.PhotoService;
import com.titaniumpanda.app.domain.ids.PhotoId;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoResourceTest {

    public static final PhotoId PHOTO_ID = new PhotoId();
    private final PhotoService photoService = mock(PhotoService.class);
    private final PhotoResource underTest = new PhotoResource(photoService);

    @Test
    public void shouldReturnPhoto() {
        PhotoDto photoDto = new PhotoDto(PHOTO_ID, "title", "photoUrl", "description", null, null, null, null);

        when(photoService.findPhotoBy(PHOTO_ID)).thenReturn(Optional.of(photoDto));

        ResponseEntity<PhotoDto> result = underTest.getPhoto(PHOTO_ID);

        assertThat(result.getBody(), is(photoDto));
    }

    @Test
    public void shouldReturnEmptyResponseWhenThereIsNoPhoto() {
        when(photoService.findPhotoBy(PHOTO_ID)).thenReturn(Optional.empty());

        assertThat(underTest.getPhoto(PHOTO_ID).hasBody(), is(false));
        assertThat(underTest.getPhoto(PHOTO_ID).getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void shouldReturnListOfPhotos() {
        List<PhotoDto> photoDtos = List.of(
                new PhotoDto(new PhotoId(), "title", "photoUrl", "description", null, null, null, null),
                new PhotoDto(new PhotoId(), "title", "photoUrl", "description", null, null, null, null),
                new PhotoDto(new PhotoId(), "title", "photoUrl", "description", null, null, null, null));

        when(photoService.findAll()).thenReturn(photoDtos);

        ResponseEntity<List<PhotoDto>> result = underTest.getAllPhotos();

        assertThat(result.getBody(), is(photoDtos));
    }

    @Test
    public void shouldReturnEmptyList() {
        when(photoService.findAll()).thenReturn(emptyList());

        ResponseEntity<List<PhotoDto>> result = underTest.getAllPhotos();

        assertThat(result.getBody(), is(emptyList()));
    }
}