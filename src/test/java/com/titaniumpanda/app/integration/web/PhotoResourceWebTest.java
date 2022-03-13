package com.titaniumpanda.app.integration.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.api.photo.PhotoRequestMetadata;
import com.titaniumpanda.app.domain.Photo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoResourceWebTest extends AbstractWebTest {

    private static final UUID CATEGORY_ID = UUID.randomUUID();
    private static final List<UUID> CATEGORY_IDS = List.of(CATEGORY_ID);
    private static final String PHOTO_BASE_URL = "baseUrl";
    private static final LocalDateTime CREATED_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MODIFIED_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final String PHOTO_DESCRIPTION = "PhotoResourceWebTest";
    private static final String PHOTO_THUMBNAIL_URL = "photoUrl";
    private static final String TITLE = "title";
    private final PhotoRequestMetadata requestMetadata = new PhotoRequestMetadata(TITLE, PHOTO_DESCRIPTION, CATEGORY_IDS);
    private static final UUID PHOTO_ID = UUID.randomUUID();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileSystemResource testPhotoFile = new FileSystemResource("src/test/resources/testimage.jpeg");

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
        mongoTestTemplate.save(photo, collectionName);

        String url = localhostWithPort + "/api/photo/" + PHOTO_ID;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        PhotoDto expected = new PhotoDto(PHOTO_ID, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        String serialisedExpectation = objectMapper.writeValueAsString(expected);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(serialisedExpectation));
    }

    @Test
    public void shouldReturnMultipleSerializedPhotoDtos() throws JsonProcessingException {
        UUID photoId1 = UUID.randomUUID();
        UUID photoId2 = UUID.randomUUID();
        UUID photoId3 = UUID.randomUUID();
        Photo photo1 = new Photo(photoId1, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        Photo photo2 = new Photo(photoId2, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        Photo photo3 = new Photo(photoId3, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);

        mongoTestTemplate.save(photo1);
        mongoTestTemplate.save(photo2);
        mongoTestTemplate.save(photo3);

        PhotoDto photoDto1 = new PhotoDto(photoId1, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        PhotoDto photoDto2 = new PhotoDto(photoId2, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);
        PhotoDto photoDto3 = new PhotoDto(photoId3, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, CATEGORY_IDS);

        String url = localhostWithPort + "/api/photo/all";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        String expected = objectMapper.writeValueAsString(List.of(photoDto1, photoDto2, photoDto3));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(expected));
    }

    @Test
    public void shouldPostNewPhoto() {
        HttpEntity<Object> request = createRequest(requestMetadata, testPhotoFile);
        String url = localhostWithPort + "/api/photo/upload";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        assertThat(responseEntity.hasBody(), is(true));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }

    private HttpEntity<Object> createRequest(PhotoRequestMetadata requestMetadata, FileSystemResource testPhotoFile) {
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("metadata", requestMetadata);
        requestBody.add("photo", testPhotoFile);
        return new HttpEntity<>(requestBody, getMultipartFormHeaders());
    }

    private HttpHeaders getMultipartFormHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        return httpHeaders;
    }
}
