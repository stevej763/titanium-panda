package com.titaniumpanda.app.integration.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.domain.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CategoryResourceWebTest extends AbstractWebTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UUID categoryId = UUID.randomUUID();
    private String localhostWithPort;

    @BeforeAll
    static void beforeAll() {
        collectionName = "category";
    }

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule()).setDateFormat(new StdDateFormat());
        localhostWithPort = "http://localhost:" + port;
    }

    @Test
    public void shouldReturnSerializedCategory() throws JsonProcessingException {
        String description = "CategoryResourceWebTest";
        Category category = new Category(categoryId, "category name", "thumbnailUrl", description);
        mongoTestTemplate.save(category, collectionName);

        String url = localhostWithPort + "/api/category/" + categoryId;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        CategoryDto expected = new CategoryDto(categoryId, "category name", "thumbnailUrl", description);
        String serialisedExpectation = objectMapper.writeValueAsString(expected);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(serialisedExpectation));
    }

    @Test
    public void shouldReturnMultipleSerializedPhotoDtos() throws JsonProcessingException {
        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();
        UUID categoryId3 = UUID.randomUUID();
        Category category1 = new Category(categoryId1, "category name", "thumbnailUrl", "description");
        Category category2 = new Category(categoryId2, "category name", "thumbnailUrl", "description");
        Category category3 = new Category(categoryId3, "category name", "thumbnailUrl", "description");
        mongoTestTemplate.save(category1);
        mongoTestTemplate.save(category2);
        mongoTestTemplate.save(category3);


        String url = localhostWithPort + "/api/category/all";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        CategoryDto categoryDto1 = new CategoryDto(categoryId1, "category name", "thumbnailUrl", "description");
        CategoryDto categoryDto2 = new CategoryDto(categoryId2, "category name", "thumbnailUrl", "description");
        CategoryDto categoryDto3 = new CategoryDto(categoryId3, "category name", "thumbnailUrl", "description");
        String expected = objectMapper.writeValueAsString(List.of(categoryDto1, categoryDto2, categoryDto3));


        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(expected));
    }

}
