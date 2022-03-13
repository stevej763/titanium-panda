package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.api.category.CategoryRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryFactoryTest {

    private final IdService idService = mock(IdService.class);

    private final UUID categoryId = UUID.randomUUID();
    private final CategoryFactory underTest = new CategoryFactory(idService);
    private final LocalDateTime createdDateTime = LocalDateTime.now();
    private final LocalDateTime modifiedDateTime = LocalDateTime.now();
    private final String categoryName = "category name";
    private final String categoryDescription = "category description";

    @Test
    public void shouldConvertToCategoryDto() {
        Category category = new Category(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
        CategoryDto categoryDto = new CategoryDto(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);

        assertThat(underTest.convertToDto(category), is(categoryDto));
    }

    @Test
    public void shouldCreateANewCategory() {
        CategoryRequest categoryRequest = new CategoryRequest(categoryName, categoryDescription);

        when(idService.createNewId()).thenReturn(categoryId);

        Category result = underTest.createNewCategory(categoryRequest);

        assertThat(result.getCategoryId(), is(categoryId));
    }

}