package com.titaniumpanda.app.webtest;

import com.titaniumpanda.app.domain.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CategoryResourceWebTest extends AbstractWebTest {

    private String locahostWithPort;

    @BeforeAll
    static void beforeAll() {
        collectionName = "category";
    }

    @BeforeEach
    @Override
    void setUp() {
        locahostWithPort = "http://localhost:" + port;
        super.setUp();
    }

    @Test
    public void shouldReturnSerializedCategory() {
        Category category = new Category("1", "category name", "thumbnailUrl", "description");
        mongoTemplate.save(category, collectionName);

        String url = locahostWithPort + "/api/category/1";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is("{" +
                "\"categoryId\":\"1\"," +
                "\"categoryName\":\"category name\"," +
                "\"categoryThumbnailUrl\":\"thumbnailUrl\"," +
                "\"categoryDescription\":\"description\"" +
                "}"));
    }

    @Test
    public void shouldReturnMultipleSerializedPhotoDtos() {
        Category category1 = new Category("1", "category name", "thumbnailUrl", "description");
        Category category2 = new Category("2", "category name", "thumbnailUrl", "description");
        Category category3 = new Category("3", "category name", "thumbnailUrl", "description");
        mongoTemplate.save(category1);
        mongoTemplate.save(category2);
        mongoTemplate.save(category3);


        String url = locahostWithPort + "/api/category/all";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(),
                is("[" +
                        "{\"categoryId\":\"1\",\"categoryName\":\"category name\",\"categoryThumbnailUrl\":\"thumbnailUrl\",\"categoryDescription\":\"description\"}," +
                        "{\"categoryId\":\"2\",\"categoryName\":\"category name\",\"categoryThumbnailUrl\":\"thumbnailUrl\",\"categoryDescription\":\"description\"}," +
                        "{\"categoryId\":\"3\",\"categoryName\":\"category name\",\"categoryThumbnailUrl\":\"thumbnailUrl\",\"categoryDescription\":\"description\"}]"
                ));
    }

}
