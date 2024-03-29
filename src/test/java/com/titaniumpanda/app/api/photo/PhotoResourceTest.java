package com.titaniumpanda.app.api.photo;

import com.titaniumpanda.app.domain.PhotoService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class PhotoResourceTest {

    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();
    private static final UUID UPLOAD_ID = UUID.randomUUID();
    private static final List<UUID> CATEGORY_IDS = List.of(CATEGORY_ID);
    private static final String PHOTO_BASE_URL = "baseUrl";
    private static final LocalDateTime CREATED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime MODIFIED_DATE_TIME = LocalDateTime.now();
    private static final String PHOTO_DESCRIPTION = "description";
    private static final String PHOTO_THUMBNAIL_URL = "photoUrl";
    private static final String PHOTO_TITLE = "title";
    private final PhotoService photoService = mock(PhotoService.class);
    private final MockMultipartFile mockPhotoFile = new MockMultipartFile("Hello", "hello".getBytes());
    private final PhotoRequestMetadata photoRequestMetadata = new PhotoRequestMetadata(PHOTO_TITLE, PHOTO_DESCRIPTION, CATEGORY_IDS);

    private final PhotoResource underTest = new PhotoResource(photoService);

    @Test
    public void shouldReturnPhoto() {
        PhotoDto photoDto = new PhotoDto(PHOTO_ID, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);

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
        UUID photoId1 = UUID.randomUUID();
        UUID photoId2 = UUID.randomUUID();
        UUID photoId3 = UUID.randomUUID();
        List<PhotoDto> photoDtos = List.of(
                new PhotoDto(photoId1, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS),
                new PhotoDto(photoId2, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS),
                new PhotoDto(photoId3, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS));

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
    public void shouldReturnPhotosForCategory() {
        UUID photoId1 = UUID.randomUUID();
        UUID photoId2 = UUID.randomUUID();
        UUID photoId3 = UUID.randomUUID();
        List<PhotoDto> photoDtos = List.of(
                new PhotoDto(photoId1, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS),
                new PhotoDto(photoId2, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS),
                new PhotoDto(photoId3, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS));

        when(photoService.findByCategoryId(CATEGORY_ID)).thenReturn(photoDtos);

        ResponseEntity<List<PhotoDto>> result = underTest.getPhotosForCategory(CATEGORY_ID);

        assertThat(result.getBody(), is(photoDtos));
    }

    @Test
    public void shouldReturnSavedPhotoOnSuccessfulPost() {
        PhotoDto photoDto =  new PhotoDto(PHOTO_ID, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);
        PhotoRequestMetadata photoUploadDetails = new PhotoRequestMetadata(PHOTO_TITLE, PHOTO_DESCRIPTION, CATEGORY_IDS);

        when(photoService.save(mockPhotoFile, photoUploadDetails)).thenReturn(Optional.of(photoDto));

        ResponseEntity<PhotoDto> result = underTest.addPhoto(photoRequestMetadata, mockPhotoFile);

        URI expectedLocation = URI.create("api/photo/" + photoDto.getPhotoIdAsString());

        assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(result.getBody(), is(photoDto));
        assertThat(result.getHeaders().getLocation(), is(expectedLocation));
    }


    @Test
    public void shouldReturnUpdatedPhotoWithNewCategory() {
        PhotoDto photoDto =  new PhotoDto(PHOTO_ID, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);
        when(photoService.addPhotoToCategory(PHOTO_ID, CATEGORY_ID)).thenReturn(Optional.of(photoDto));

        ResponseEntity<PhotoDto> result = underTest.addPhotoToCategory(PHOTO_ID, CATEGORY_ID);
        assertThat(result.getBody(), Is.is(photoDto));
    }

    @Test
    public void shouldReturnUpdatedPhotoWithRemovedCategory() {
        PhotoDto photoDto =  new PhotoDto(PHOTO_ID, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);
        when(photoService.removePhotoFromCategory(PHOTO_ID, CATEGORY_ID)).thenReturn(Optional.of(photoDto));

        ResponseEntity<PhotoDto> result = underTest.removePhotoFromCategory(PHOTO_ID, CATEGORY_ID);
        assertThat(result.getBody(), Is.is(photoDto));
    }

    @Test
    public void shouldReturnErrorOnFailureToSaveNewPhoto() {
        PhotoRequestMetadata photoUploadDetails = new PhotoRequestMetadata(PHOTO_TITLE, PHOTO_DESCRIPTION, CATEGORY_IDS);

        when(photoService.save(mockPhotoFile, photoUploadDetails)).thenReturn(Optional.empty());

        ResponseEntity<PhotoDto> result = underTest.addPhoto(photoRequestMetadata, mockPhotoFile);

        assertThat(result.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(result.hasBody(), is(false));
    }

    @Test
    public void shouldReturnOKIfPhotoDeleted() {
        when(photoService.deletePhoto(PHOTO_ID)).thenReturn(true);

        ResponseEntity<Boolean> result = underTest.deleteCategory(PHOTO_ID);

        assertThat(result.hasBody(), is(false));
        assertThat(result.getStatusCode(), is(OK));
    }

    @Test
    public void shouldReturn404IfProblemDeletingCategory() {
        when(photoService.deletePhoto(PHOTO_ID)).thenReturn(false);

        ResponseEntity<Boolean> result = underTest.deleteCategory(PHOTO_ID);

        assertThat(result.hasBody(), is(false));
        assertThat(result.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void shouldUpdatePhoto() {
        PhotoDto photoDto =  new PhotoDto(PHOTO_ID, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);
        PhotoUpdateRequest photoUpdateRequest = new PhotoUpdateRequest(UUID.randomUUID(), PHOTO_TITLE, PHOTO_DESCRIPTION);

        when(photoService.updatePhoto(photoUpdateRequest)).thenReturn(Optional.of(photoDto));

        ResponseEntity<PhotoDto> result = underTest.updatePhoto(photoUpdateRequest);

        assertThat(result.getStatusCode(), Is.is(OK));
        assertThat(result.getBody(), Is.is(photoDto));
    }

    @Test
    public void shouldReturnRandomPhotoForCategory() {
        PhotoDto photoDto = new PhotoDto(PHOTO_ID, PHOTO_TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);

        when(photoService.findRandomPhotoForCategory(CATEGORY_ID)).thenReturn(Optional.of(photoDto));

        ResponseEntity<PhotoDto> result = underTest.getRandomPhotoForCategory(CATEGORY_ID);

        assertThat(result.getBody(), is(photoDto));
    }
}