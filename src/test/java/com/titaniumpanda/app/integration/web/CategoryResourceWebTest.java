package com.titaniumpanda.app.integration.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.api.category.CategoryRequest;
import com.titaniumpanda.app.domain.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CategoryResourceWebTest extends AbstractWebTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UUID categoryId = UUID.randomUUID();
    private static final LocalDateTime createdDateTime = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime modifiedDateTime = LocalDateTime.of(1, 1, 1, 0, 0);
    private final String description = "CategoryResourceWebTest";
    private final String categoryName = "category name";
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
        Category category = new Category(categoryId, categoryName, description, createdDateTime, modifiedDateTime);
        mongoTestTemplate.save(category, collectionName);

        String url = localhostWithPort + "/api/category/" + categoryId;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        CategoryDto expected = new CategoryDto(categoryId, "category name", description, createdDateTime, modifiedDateTime);
        String serialisedExpectation = objectMapper.writeValueAsString(expected);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(serialisedExpectation));
    }

    @Test
    public void shouldReturnMultipleSerializedPhotoDtos() throws JsonProcessingException {
        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();
        UUID categoryId3 = UUID.randomUUID();
        Category category1 = new Category(categoryId1, categoryName, description, createdDateTime, modifiedDateTime);
        Category category2 = new Category(categoryId2, categoryName, description, createdDateTime, modifiedDateTime);
        Category category3 = new Category(categoryId3, categoryName, description, createdDateTime, modifiedDateTime);
        mongoTestTemplate.save(category1);
        mongoTestTemplate.save(category2);
        mongoTestTemplate.save(category3);


        String url = localhostWithPort + "/api/category/all";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        CategoryDto categoryDto1 = new CategoryDto(categoryId1, categoryName, description, createdDateTime, modifiedDateTime);
        CategoryDto categoryDto2 = new CategoryDto(categoryId2, categoryName, description, createdDateTime, modifiedDateTime);
        CategoryDto categoryDto3 = new CategoryDto(categoryId3, categoryName, description, createdDateTime, modifiedDateTime);
        String expected = objectMapper.writeValueAsString(List.of(categoryDto1, categoryDto2, categoryDto3));


        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(expected));
    }

    @Test
    public void shouldPostNewCategory() {
        CategoryRequest categoryRequest = new CategoryRequest("category name", "category description");
        HttpEntity<Object> request = createRequest(categoryRequest);
        String url = localhostWithPort + "/api/category/upload";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(responseEntity.hasBody(), is(true));
    }

    private HttpEntity<Object> createRequest(CategoryRequest categoryRequest) {
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("categoryRequest", categoryRequest);
        return new HttpEntity<>(requestBody, getMultipartFormHeaders());
    }

    private HttpHeaders getMultipartFormHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        return httpHeaders;
    }
}
