package com.titaniumpanda.app.webtest;

import com.titaniumpanda.app.domain.Photo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoResourceWebTest extends AbstractWebTest {

    private String localhostWithPort;

    @BeforeAll
    static void beforeAll() {
        collectionName = "photo";
    }

    @BeforeEach
    @Override
    void setUp() {
        localhostWithPort = "http://localhost:" + port;
        super.setUp();
    }

    @Test
    public void shouldReturnSerializedPhotoDto() {
        Photo photo = new Photo("photo title", "1", "photoUrl", "description");
        mongoTemplate.save(photo, "photo");

        String url = localhostWithPort + "/api/photo/1";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is("{\"title\":\"photo title\",\"photoId\":\"1\",\"photoUrl\":\"photoUrl\",\"description\":\"description\"}"));
    }

    @Test
    public void shouldReturnMultipleSerializedPhotoDtos() {
        Photo photo1 = new Photo("photo title", "1", "photoUrl", "description");
        Photo photo2 = new Photo("photo title", "2", "photoUrl", "description");
        Photo photo3 = new Photo("photo title", "3", "photoUrl", "description");
        mongoTemplate.save(photo1);
        mongoTemplate.save(photo2);
        mongoTemplate.save(photo3);

        String url = localhostWithPort + "/api/photo/all";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(),
                is("[" +
                        "{\"title\":\"photo title\",\"photoId\":\"1\",\"photoUrl\":\"photoUrl\",\"description\":\"description\"}," +
                        "{\"title\":\"photo title\",\"photoId\":\"2\",\"photoUrl\":\"photoUrl\",\"description\":\"description\"}," +
                        "{\"title\":\"photo title\",\"photoId\":\"3\",\"photoUrl\":\"photoUrl\",\"description\":\"description\"}]"
                ));
    }

}
