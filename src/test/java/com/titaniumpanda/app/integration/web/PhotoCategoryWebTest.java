package com.titaniumpanda.app.integration.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.domain.Category;
import com.titaniumpanda.app.domain.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoCategoryWebTest extends AbstractWebTest {

    private static final UUID CATEGORY_ID = UUID.randomUUID();
    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final UUID UPLOAD_ID = UUID.randomUUID();
    private static final List<UUID> CATEGORY_IDS = List.of(CATEGORY_ID);
    private static final LocalDateTime CREATED_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MODIFIED_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final String PHOTO_DESCRIPTION = "PhotoResourceWebTest";
    private static final String TITLE = "title";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String localhostWithPort;


    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule()).setDateFormat(new StdDateFormat());
        localhostWithPort = "http://localhost:" + port;
    }

    @Test
    public void shouldAddPhotoToCategory() {
        Photo photo = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, emptyList());
        Category category = new Category(CATEGORY_ID, "name", "description", CREATED_DATE_TIME, MODIFIED_DATE_TIME);

        mongoTestTemplate.save(photo);
        mongoTestTemplate.save(category);
        String url = localhostWithPort + String.format("/api/photo/%s/category/add/%s", PHOTO_ID, CATEGORY_ID);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null, String.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void shouldRemovePhotoFromCategory() {
        Photo photo = new Photo(PHOTO_ID, TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);
        Category category = new Category(CATEGORY_ID, "name", "description", CREATED_DATE_TIME, MODIFIED_DATE_TIME);

        mongoTestTemplate.save(photo);
        mongoTestTemplate.save(category);
        String url = localhostWithPort + String.format("/api/photo/%s/category/delete/%s", PHOTO_ID, CATEGORY_ID);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null, String.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void shouldReturnPhotoDtoListForCategory() throws JsonProcessingException {
        UUID photoId1 = UUID.randomUUID();
        UUID photoId2 = UUID.randomUUID();
        UUID photoId3 = UUID.randomUUID();
        UUID photoId4 = UUID.randomUUID();
        Photo photo1 = new Photo(photoId1, TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);
        Photo photo2 = new Photo(photoId2, TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);
        Photo photo3 = new Photo(photoId3, TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);
        Photo photo4 = new Photo(photoId4, TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, emptyList());

        mongoTestTemplate.save(photo1);
        mongoTestTemplate.save(photo2);
        mongoTestTemplate.save(photo3);
        mongoTestTemplate.save(photo4);

        PhotoDto photoDto1 = new PhotoDto(photoId1, TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);
        PhotoDto photoDto2 = new PhotoDto(photoId2, TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);
        PhotoDto photoDto3 = new PhotoDto(photoId3, TITLE, UPLOAD_ID, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, CATEGORY_IDS);

        String url = localhostWithPort + String.format("/api/photo/category/%s", CATEGORY_ID);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        String expected = objectMapper.writeValueAsString(List.of(photoDto1, photoDto2, photoDto3));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(expected));
    }

}
