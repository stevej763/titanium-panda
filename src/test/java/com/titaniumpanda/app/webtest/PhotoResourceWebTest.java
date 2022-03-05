package com.titaniumpanda.app.webtest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoResourceWebTest extends AbstractWebTest {

    @Test
    public void shouldReturnSerializedPhotoDto() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/api/photo/1", String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is("{\"title\":\"photo title\",\"photoId\":\"1\",\"photoUrl\":\"photoUrl\",\"description\":\"description\"}"));
    }

    @Test
    public void shouldReturnMultipleSerializedPhotoDtos() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/api/photo/all", String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(),
                is("[" +
                        "{\"title\":\"photo title\",\"photoId\":\"1\",\"photoUrl\":\"photoUrl\",\"description\":\"description\"}," +
                        "{\"title\":\"photo title\",\"photoId\":\"3\",\"photoUrl\":\"photoUrl\",\"description\":\"description\"}," +
                        "{\"title\":\"photo title\",\"photoId\":\"2\",\"photoUrl\":\"photoUrl\",\"description\":\"description\"}]"
                ));
    }

}
