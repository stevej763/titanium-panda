package com.titaniumpanda.app.api.photo;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.domain.PhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoResourceTest {

    private final PhotoService photoService = mock(PhotoService.class);
    private final PhotoResource underTest = new PhotoResource(photoService);

    @Test
    public void shouldReturnPhoto() {
        String id = UUID.randomUUID().toString();
        PhotoDto photoDto = new PhotoDto("photo title", id, "photoUrl", "description");

        when(photoService.findPhotoBy(id)).thenReturn(Optional.of(photoDto));

        ResponseEntity<PhotoDto> result = underTest.getPhoto(id);

        assertThat(result.getBody(), is(photoDto));
    }

    @Test
    public void shouldReturnEmptyResponseWhenThereIsNoPhoto() {
        String id = UUID.randomUUID().toString();
        when(photoService.findPhotoBy(id)).thenReturn(Optional.empty());
        assertThat(underTest.getPhoto(id).hasBody(), is(false));
        assertThat(underTest.getPhoto(id).getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void shouldReturnListOfPhotos() {
        List<PhotoDto> photoDtos = List.of(
                new PhotoDto("photo title", "1", "photoUrl", "description"),
                new PhotoDto("photo title", "2", "photoUrl", "description"),
                new PhotoDto("photo title", "3", "photoUrl", "description"));

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