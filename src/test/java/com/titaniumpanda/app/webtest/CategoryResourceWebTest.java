package com.titaniumpanda.app.webtest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CategoryResourceWebTest extends AbstractWebTest {

    @Test
    public void shouldReturnSerializedCategory() {
        String url = localhostWithPort + "/api/category/1";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is("{" +
                "\"categoryId\":\"1\"," +
                "\"categoryName\":\"title\"," +
                "\"categoryThumbnailUrl\":\"thumbnailUrl\"," +
                "\"categoryDescription\":\"description\"" +
                "}"));
    }

    @Test
    public void shouldReturnMultipleSerializedPhotoDtos() {
        String url = localhostWithPort + "/api/category/all";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(),
                is("[" +
                        "{\"categoryId\":\"1\",\"categoryName\":\"title\",\"categoryThumbnailUrl\":\"thumbnailUrl\",\"categoryDescription\":\"description\"}," +
                        "{\"categoryId\":\"2\",\"categoryName\":\"title\",\"categoryThumbnailUrl\":\"thumbnailUrl\",\"categoryDescription\":\"description\"}," +
                        "{\"categoryId\":\"3\",\"categoryName\":\"title\",\"categoryThumbnailUrl\":\"thumbnailUrl\",\"categoryDescription\":\"description\"}]"
                ));
    }

}
