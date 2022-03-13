package com.titaniumpanda.app.api.category;

import com.titaniumpanda.app.domain.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;

public class CategoryResourceTest {

    private final CategoryService categoryService = mock(CategoryService.class);
    private final CategoryResource underTest = new CategoryResource(categoryService);
    private final UUID id = UUID.randomUUID();
    private final String categoryName = "title";
    private final String categoryDescription = "description";
    private final LocalDateTime createdDateTime = LocalDateTime.now();
    private final LocalDateTime modifiedDateTime = LocalDateTime.now();

    @Test
    public void shouldReturnCategory() {
        CategoryDto categoryDto = new CategoryDto(id, categoryName, categoryDescription, createdDateTime, modifiedDateTime);

        when(categoryService.findBy(id)).thenReturn(Optional.of(categoryDto));

        ResponseEntity<CategoryDto> result = underTest.getCategory(id);

        assertThat(result.getBody(), is(categoryDto));
    }

    @Test
    public void shouldReturnEmptyResponseIfNoCategoryFound() {
        when(categoryService.findBy(id)).thenReturn(Optional.empty());

        ResponseEntity<CategoryDto> result = underTest.getCategory(id);

        assertThat(result.hasBody(), is(false));
        assertThat(result.getStatusCode(), is(NO_CONTENT));
    }

    @Test
    public void shouldReturnListOfCatergories() {
        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();
        UUID categoryId3 = UUID.randomUUID();
        List<CategoryDto> categoryDtos = List.of(
                new CategoryDto(categoryId1, "title", "description", createdDateTime, modifiedDateTime),
                new CategoryDto(categoryId2, "title", "description", createdDateTime, modifiedDateTime),
                new CategoryDto(categoryId3, "title", "description", createdDateTime, modifiedDateTime));

        when(categoryService.findAll()).thenReturn(categoryDtos);

        ResponseEntity<List<CategoryDto>> result = underTest.getAllCategories();

        assertThat(result.getBody(), is(categoryDtos));
    }

    @Test
    public void shouldReturnEmptyList() {
        when(categoryService.findAll()).thenReturn(emptyList());

        ResponseEntity<List<CategoryDto>> result = underTest.getAllCategories();

        assertThat(result.getBody(), is(emptyList()));
    }

    @Test
    public void shouldReturnSavedCategoryOnSuccessfulPost() {
        CategoryDto categoryDto = new CategoryDto(id, "title", "description", createdDateTime, modifiedDateTime);
        CategoryRequest categoryRequest = new CategoryRequest("category name", "category description");

        when(categoryService.save(categoryRequest)).thenReturn(Optional.of(categoryDto));

        ResponseEntity<CategoryDto> result = underTest.addPhoto(categoryRequest);

        URI expectedLocation = URI.create("api/category/" + categoryDto.getCategoryId());

        assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(result.getBody(), is(categoryDto));
        assertThat(result.getHeaders().getLocation(), is(expectedLocation));
    }

    @Test
    public void shouldReturnErrorOnFailureToSaveNewCategory() {
        CategoryRequest categoryRequest = new CategoryRequest("category name", "category description");

        when(categoryService.save(categoryRequest)).thenReturn(Optional.empty());

        ResponseEntity<CategoryDto> result = underTest.addPhoto(categoryRequest);

        assertThat(result.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(result.hasBody(), is(false));
    }
}
