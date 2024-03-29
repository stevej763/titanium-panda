package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.external.PhotoUploadDetail;
import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.api.photo.PhotoRequestMetadata;
import com.titaniumpanda.app.api.photo.PhotoUpdateRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoFactoryTest {

    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final UUID UPLOAD_ID = UUID.randomUUID();
    private static final String TITLE = "title";
    private static final String PHOTO_THUMBNAIL_URL = "photoUrl";
    private static final String DESCRIPTION = "description";
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    private static final String PHOTO_BASE_URL = "photoBaseUrl";
    private static final UUID CATEGORY_ID = UUID.randomUUID();
    private static final List<UUID> CATEGORY_IDS = List.of(CATEGORY_ID);
    private final IdService idService = mock(IdService.class);
    private final PhotoFactory underTest = new PhotoFactory(idService);

    private final Photo photo = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, CATEGORY_IDS);
    private final PhotoDto dto = new PhotoDto(PHOTO_ID, TITLE, UPLOAD_ID, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, CATEGORY_IDS);

    @Test
    public void shouldConvertToDto() {
        assertThat(underTest.convertToDto(photo), is(dto));
    }

    @Test
    public void shouldCreateANewPhoto() {
        PhotoRequestMetadata metadata = new PhotoRequestMetadata(TITLE, DESCRIPTION, CATEGORY_IDS);
        PhotoUploadDetail photoUploadDetail = new PhotoUploadDetail(true, UUID.randomUUID(), "key");

        when(idService.createNewId()).thenReturn(PHOTO_ID);

        Photo result = underTest.createNewPhoto(photoUploadDetail, metadata);

        assertThat(result.getPhotoId(), is(photo.getPhotoId()));
    }

    @Test
    public void shouldReturnUpdatedPhotoWithNewName() {
        String updatedTitle = "new title";
        String updatedDescription = "new description";
        PhotoUpdateRequest updateRequest = new PhotoUpdateRequest(PHOTO_ID, updatedTitle, updatedDescription);
        Photo oldPhoto = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, CATEGORY_IDS);
        Photo updatedPhoto = new Photo(PHOTO_ID, updatedTitle, UPLOAD_ID, updatedDescription, LOCAL_DATE_TIME, LOCAL_DATE_TIME, CATEGORY_IDS);

        Photo result = underTest.updatePhoto(oldPhoto, updateRequest);

        assertThat(result.getPhotoId(), is(updatedPhoto.getPhotoId()));
        assertThat(result.getPhotoTitle(), is(updatedTitle));
        assertThat(result.getDescription(), is(updatedDescription));
        assertThat(result.getModifiedDateTime(), not(oldPhoto.getModifiedDateTime()));
    }

    @Test
    public void shouldReturnUpdatedPhotoWithCategoryIdInserted() {
        Photo oldPhoto = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, Collections.emptyList());
        Photo updatedPhoto = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, CATEGORY_IDS);

        Photo result = underTest.updatePhotoWithNewCategory(oldPhoto, CATEGORY_ID);

        assertThat(result.getPhotoId(), is(updatedPhoto.getPhotoId()));
        assertThat(result.getCategoryIds(), is(updatedPhoto.getCategoryIds()));
        assertThat(result.getModifiedDateTime(), not(oldPhoto.getModifiedDateTime()));
    }

    @Test
    public void shouldReturnUpdatedPhotoWithCategoryIdRemoved() {
        Photo photo = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, CATEGORY_IDS);
        Photo updatedPhoto = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, Collections.emptyList());

        Photo result = underTest.updatePhotoWithCategoryRemoved(photo, CATEGORY_ID);

        assertThat(result.getPhotoId(), is(updatedPhoto.getPhotoId()));
        assertThat(result.getCategoryIds(), is(updatedPhoto.getCategoryIds()));
        assertThat(result.getModifiedDateTime(), not(photo.getModifiedDateTime()));
    }

    @Test
    public void shouldReturnUpdatedPhotoWithRemainingCategories() {
        UUID category1 = UUID.randomUUID();
        UUID category2 = UUID.randomUUID();
        UUID category3 = UUID.randomUUID();
        List<UUID> oldCategories = List.of(category1, category2, category3);
        List<UUID> updatedCategories = List.of(category1, category2);
        Photo photo = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, oldCategories);
        Photo updatedPhoto = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, updatedCategories);

        Photo result = underTest.updatePhotoWithCategoryRemoved(photo, category3);

        assertThat(result.getPhotoId(), is(updatedPhoto.getPhotoId()));
        assertThat(result.getCategoryIds(), is(updatedPhoto.getCategoryIds()));
        assertThat(result.getModifiedDateTime(), not(photo.getModifiedDateTime()));
    }

    @Test
    public void shouldReturnUpdatedPhotoWithBothOldAndNewCategories() {
        UUID category1 = UUID.randomUUID();
        UUID category2 = UUID.randomUUID();
        UUID category3 = UUID.randomUUID();
        List<UUID> oldCategories = List.of(category1, category2);
        List<UUID> updatedCategories = List.of(category1, category2, category3);
        Photo oldPhoto = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, oldCategories);
        Photo updatedPhoto = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, updatedCategories);

        Photo result = underTest.updatePhotoWithNewCategory(oldPhoto, category3);

        assertThat(result.getPhotoId(), is(updatedPhoto.getPhotoId()));
        assertThat(result.getCategoryIds(), is(updatedPhoto.getCategoryIds()));
        assertThat(result.getModifiedDateTime(), not(oldPhoto.getModifiedDateTime()));
    }
}