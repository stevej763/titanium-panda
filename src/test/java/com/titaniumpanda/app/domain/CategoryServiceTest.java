package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.repository.CategoryRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final CategoryFactory categoryFactory = mock(CategoryFactory.class);
    private final CategoryService underTest = new CategoryService(categoryRepository, categoryFactory);
    private final UUID categoryId = UUID.randomUUID();

    @Test
    public void shouldReturnCategoryDto() {
        CategoryDto categoryDto = new CategoryDto(categoryId, "title", "thumbnailUrl", "description");
        Category category = new Category(UUID.randomUUID(), "title", "thumbnailUrl", "description");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryFactory.convertToDto(category)).thenReturn(categoryDto);

        Optional<CategoryDto> result = underTest.findBy(categoryId);

        assertThat(result, is(Optional.of(categoryDto)));
    }

    @Test
    public void shouldReturnOptionalEmptyIfIdNotFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        assertThat(underTest.findBy(categoryId), is(Optional.empty()));
    }

    @Test
    public void shouldReturnSetOfAllCategories() {
        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();
        UUID categoryId3 = UUID.randomUUID();
        Category category1 =new Category(categoryId1, "title", "thumbnailUrl", "description");
        Category category2 =new Category(categoryId2, "title", "thumbnailUrl", "description");
        Category category3 =new Category(categoryId3, "title", "thumbnailUrl", "description");
        List<Category> categories = List.of(
                category1,
                category2,
                category3
        );

        CategoryDto categoryDto1 =new CategoryDto(categoryId, "title", "thumbnailUrl", "description");
        CategoryDto categoryDto2 =new CategoryDto(categoryId, "title", "thumbnailUrl", "description");
        CategoryDto categoryDto3 =new CategoryDto(categoryId, "title", "thumbnailUrl", "description");
        List<CategoryDto> categoryDtos = List.of(
                categoryDto1,
                categoryDto2,
                categoryDto3
        );

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryFactory.convertToDto(category1)).thenReturn(categoryDto1);
        when(categoryFactory.convertToDto(category2)).thenReturn(categoryDto2);
        when(categoryFactory.convertToDto(category3)).thenReturn(categoryDto3);

        assertThat(underTest.findAll(), is(categoryDtos));
    }

    @Test
    public void shouldReturnEmptyListIfNoCategoriesFound() {
        assertThat(underTest.findAll(), is(emptyList()));
    }
}