package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.api.photo.PhotoRequestMetadata;
import com.titaniumpanda.app.domain.ids.CategoryId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoFactoryTest {

    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final String TITLE = "title";
    private static final String PHOTO_THUMBNAIL_URL = "photoUrl";
    private static final String DESCRIPTION = "description";
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    private static final String PHOTO_BASE_URL = "photoBaseUrl";
    private static final List<UUID> CATEGORY_IDS = List.of(UUID.randomUUID());
    private final IdService idService = mock(IdService.class);
    private final PhotoFactory underTest = new PhotoFactory(idService);

    private final Photo photo = new Photo(PHOTO_ID, TITLE, PHOTO_THUMBNAIL_URL, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
    private final PhotoDto dto = new PhotoDto(PHOTO_ID, TITLE, PHOTO_THUMBNAIL_URL, DESCRIPTION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    public void shouldConvertToDto() {
        assertThat(underTest.convertToDto(photo), is(dto));
    }

    @Test
    public void shouldCreateANewPhoto() {
        Instant.now(
                Clock.fixed(
                        Instant.parse( "2016-01-23T12:34:56Z"), ZoneOffset.UTC
                )
        );
        PhotoRequestMetadata metadata = new PhotoRequestMetadata(TITLE, DESCRIPTION, CATEGORY_IDS);
        PhotoUploadDetails photoUploadDetails = new PhotoUploadDetails(PHOTO_THUMBNAIL_URL, PHOTO_BASE_URL);

        when(idService.createNewId()).thenReturn(PHOTO_ID);

        Photo result = underTest.createNewPhoto(photoUploadDetails, metadata);
        assertThat(result.getPhotoId(), is(photo.getPhotoId()));
    }

}