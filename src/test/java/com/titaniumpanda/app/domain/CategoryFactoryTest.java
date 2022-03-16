package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.api.category.CategoryRequest;
import com.titaniumpanda.app.api.category.CategoryUpdateRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
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
    private final CategoryDto categoryDto = new CategoryDto(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
    private final Category category = new Category(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);

    @Test
    public void shouldConvertToCategoryDto() {

        assertThat(underTest.convertToDto(category), is(categoryDto));
    }

    @Test
    public void shouldCreateANewCategory() {
        CategoryRequest categoryRequest = new CategoryRequest(categoryName, categoryDescription);

        when(idService.createNewId()).thenReturn(categoryId);

        Category result = underTest.createNewCategory(categoryRequest);

        assertThat(result.getCategoryId(), is(categoryId));
    }

    @Test
    public void shouldUpdateExistingCategory() {
        String updatedName = "new name";
        String updatedDescription = "new description";
        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest(categoryId.toString(), updatedName, updatedDescription);
        Category updatedCategory = new Category(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);

        Category result = underTest.updateCategory(updatedCategory, categoryUpdateRequest);

        assertThat(result.getCategoryId(), is(categoryId));
        assertThat(result.getCategoryName(), is(updatedName));
        assertThat(result.getCategoryDescription(), is(updatedDescription));
        assertThat(result.getModifiedDateTime(), not(modifiedDateTime));
    }

}