package com.example.titaniumpanda.api.photos;

import com.example.titaniumpanda.domain.PhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

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
        PhotoDto photoDto = new PhotoDto("photo title", id);
        when(photoService.findPhotoBy(id)).thenReturn(Optional.of(photoDto));
        assertThat(underTest.getPhoto(id).getBody(), is(photoDto));
    }

    @Test
    public void shouldReturnEmptyResponseWhenThereIsNoPhoto() {
        String id = UUID.randomUUID().toString();
        when(photoService.findPhotoBy(id)).thenReturn(Optional.empty());
        assertThat(underTest.getPhoto(id).hasBody(), is(false));
        assertThat(underTest.getPhoto(id).getStatusCode(), is(HttpStatus.NO_CONTENT));
    }
}