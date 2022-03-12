package com.titaniumpanda.app.api.photo;

import com.titaniumpanda.app.domain.PhotoService;
import com.titaniumpanda.app.domain.PhotoUploadService;
import com.titaniumpanda.app.domain.ids.CategoryId;
import com.titaniumpanda.app.domain.ids.PhotoId;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoResourceTest {

    private static final PhotoId PHOTO_ID = new PhotoId();
    private static final CategoryId CATEGORY_ID = new CategoryId();
    private final List<CategoryId> categoryIds = List.of(CATEGORY_ID);
    private static final String PHOTO_BASE_URL = "baseUrl";
    private static final LocalDateTime CREATED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime MODIFIED_DATE_TIME = LocalDateTime.now();
    private static final String PHOTO_DESCRIPTION = "description";
    private static final String PHOTO_THUMBNAIL_URL = "photoUrl";
    private static final String TITLE = "title";
    private final PhotoService photoService = mock(PhotoService.class);
    private final PhotoUploadService photoUploadService = mock(PhotoUploadService.class);
    private final PhotoResource underTest = new PhotoResource(photoService, photoUploadService);
    private final MockMultipartFile mockPhotoFile = new MockMultipartFile("Hello", "hello".getBytes());
    private final PhotoRequestMetadata photoRequestMetadata = new PhotoRequestMetadata(TITLE, PHOTO_DESCRIPTION, categoryIds);

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

        ResponseEntity<PhotoDto> result = underTest.getPhoto(PHOTO_ID);

        assertThat(result.hasBody(), is(false));
        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
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

    @Test
    public void shouldReturnSavedPhotoOnSuccessfulPost() {
        PhotoDto photoDto = new PhotoDto(PHOTO_ID, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL,categoryIds);
        PhotoRequestMetadata photoUploadDetails = new PhotoRequestMetadata(TITLE, PHOTO_DESCRIPTION, categoryIds);

        when(photoService.save(mockPhotoFile, photoUploadDetails)).thenReturn(Optional.of(photoDto));

        ResponseEntity<PhotoDto> result = underTest.addPhoto(photoRequestMetadata, mockPhotoFile);

        URI expectedLocation = URI.create("api/photo/" + photoDto.getPhotoIdAsString());

        assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(result.getBody(), is(photoDto));
        assertThat(result.getHeaders().getLocation(), is(expectedLocation));
    }

    @Test
    public void shouldReturnErrorOnFailureToSaveNewPhoto() {
        PhotoRequestMetadata photoUploadDetails = new PhotoRequestMetadata(TITLE, PHOTO_DESCRIPTION, categoryIds);

        when(photoService.save(mockPhotoFile, photoUploadDetails)).thenReturn(Optional.empty());

        ResponseEntity<PhotoDto> result = underTest.addPhoto(photoRequestMetadata, mockPhotoFile);

        assertThat(result.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(result.hasBody(), is(false));
    }
}