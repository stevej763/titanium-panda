package com.titaniumpanda.app.webtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.domain.Photo;
import com.titaniumpanda.app.domain.ids.CategoryId;
import com.titaniumpanda.app.domain.ids.PhotoId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoResourceWebTest extends AbstractWebTest {

    private static final CategoryId CATEGORY_ID = new CategoryId();
    private static final List<CategoryId> CATEGORY_IDS = List.of(CATEGORY_ID);
    private static final String PHOTO_BASE_URL = "baseUrl";
    private static final LocalDateTime CREATED_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MODIFIED_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final String PHOTO_DESCRIPTION = "description";
    private static final String PHOTO_THUMBNAIL_URL = "photoUrl";
    private static final String TITLE = "title";
    private static final PhotoId PHOTO_ID = new PhotoId();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String localhostWithPort;

    @BeforeAll
    static void beforeAll() {
        collectionName = "photo";

    }

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule()).setDateFormat(new StdDateFormat());
        localhostWithPort = "http://localhost:" + port;
    }

    @Test
    public void shouldReturnSerializedPhotoDto() throws JsonProcessingException {
        Photo photo = new Photo(PHOTO_ID, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        mongoTemplate.save(photo, collectionName);

        String url = localhostWithPort + "/api/photo/" + PHOTO_ID.getId();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        PhotoDto expected = new PhotoDto(PHOTO_ID, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        String serialisedExpectation = objectMapper.writeValueAsString(expected);
        assertThat(responseEntity.getBody(), is(serialisedExpectation));
    }

    @Test
    public void shouldReturnMultipleSerializedPhotoDtos() throws JsonProcessingException {
        PhotoId photoId1 = new PhotoId();
        PhotoId photoId2 = new PhotoId();
        PhotoId photoId3 = new PhotoId();
        Photo photo1 = new Photo(photoId1, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        Photo photo2 = new Photo(photoId2, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        Photo photo3 = new Photo(photoId3, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);

        mongoTemplate.save(photo1);
        mongoTemplate.save(photo2);
        mongoTemplate.save(photo3);

        PhotoDto photoDto1 = new PhotoDto(photoId1, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        PhotoDto photoDto2 = new PhotoDto(photoId2, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        PhotoDto photoDto3 = new PhotoDto(photoId3, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);

        String url = localhostWithPort + "/api/photo/all";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        String expected = objectMapper.writeValueAsString(List.of(photoDto1, photoDto2, photoDto3));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(expected));
    }

}
