package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.external.PhotoUploadResource;
import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.api.photo.PhotoRequestMetadata;
import com.titaniumpanda.app.repository.PhotoRepository;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoServiceTest {

    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();
    private static final List<UUID> CATEGORY_IDS = List.of(CATEGORY_ID);
    private static final String PHOTO_BASE_URL = "baseUrl";
    private static final LocalDateTime CREATED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime MODIFIED_DATE_TIME = LocalDateTime.now();
    private static final String PHOTO_DESCRIPTION = "description";
    private static final String PHOTO_THUMBNAIL_URL = "photoUrl";
    private static final String TITLE = "title";
    private final PhotoFactory photoFactory = mock(PhotoFactory.class);
    private final PhotoRepository photoRepository = mock(PhotoRepository.class);
    private final PhotoUploadResource photoUploadResource = mock(PhotoUploadResource.class);

    private final PhotoService underTest = new PhotoService(photoFactory, photoRepository, photoUploadResource);

    @Test
    public void shouldReturnPhotoDto() {
        PhotoDto photoDto = new PhotoDto(PHOTO_ID, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        Photo photo = new Photo(PHOTO_ID, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        when(photoRepository.findById(PHOTO_ID)).thenReturn(Optional.of(photo));
        when(photoFactory.convertToDto(photo)).thenReturn(photoDto);
        assertThat(underTest.findPhotoBy(PHOTO_ID), is(Optional.of(photoDto)));
    }

    @Test
    public void shouldReturnOptionalEmptyIfIdNotFound() {
        when(photoRepository.findById(PHOTO_ID)).thenReturn(Optional.empty());
        assertThat(underTest.findPhotoBy(PHOTO_ID), is(Optional.empty()));
    }

    @Test
    public void shouldReturnSetOfPhotoDtoObjects() {
        UUID photoId1 = UUID.randomUUID();
        UUID photoId2 = UUID.randomUUID();
        UUID photoId3 = UUID.randomUUID();
        Photo photo1 = new Photo(photoId1, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        Photo photo2 = new Photo(photoId2, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        Photo photo3 = new Photo(photoId3, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        List<Photo> photos = List.of(
                photo1,
                photo2,
                photo3
        );
        PhotoDto photoDto1 = new PhotoDto(photoId1, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        PhotoDto photoDto2 = new PhotoDto(photoId2, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        PhotoDto photoDto3 = new PhotoDto(photoId3, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
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

    @Test
    public void shouldReturnPhotoDtoOnSuccess() {
        PhotoDto photoDto = new PhotoDto(PHOTO_ID, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        Photo photo = new Photo(PHOTO_ID, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);

        String photoThumbnailUrl = "/thisIsWhereTheThumbnailLives";
        String photoBaseUrl = "/thisIsWhereTheImageIsLocated";
        PhotoRequestMetadata metadata = new PhotoRequestMetadata("photoTitle", "photoDescription", emptyList());
        MockMultipartFile photoFile = new MockMultipartFile("photo", "photo".getBytes());
        PhotoUploadDetails photoUploadDetails = new PhotoUploadDetails(photoThumbnailUrl, photoBaseUrl);

        when(photoUploadResource.upload(photoFile)).thenReturn(Optional.of(photoUploadDetails));
        when(photoFactory.createNewPhoto(photoUploadDetails, metadata)).thenReturn(photo);
        when(photoRepository.save(photo)).thenReturn(photo);
        when(photoFactory.convertToDto(photo)).thenReturn(photoDto);

        Optional<PhotoDto> result = underTest.save(photoFile, metadata);

        assertThat(result, Is.is(Optional.of(photoDto)));
    }
}