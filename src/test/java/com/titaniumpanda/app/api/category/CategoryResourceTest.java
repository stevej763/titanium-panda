package com.titaniumpanda.app.api.category;

import com.titaniumpanda.app.domain.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;

public class CategoryResourceTest {

    private final CategoryService categoryService = mock(CategoryService.class);
    private final CategoryResource underTest = new CategoryResource(categoryService);
    private final String id = "1";

    @Test
    public void shouldReturnCategory() {
        CategoryDto categoryDto = new CategoryDto("id", "title", "thumbnailUrl", "description");

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
        List<CategoryDto> categoryDtos = List.of(
                new CategoryDto("1", "title", "thumbnailUrl", "description"),
                new CategoryDto("2", "title", "thumbnailUrl", "description"),
                new CategoryDto("3", "title", "thumbnailUrl", "description"));

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
}
