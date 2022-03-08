package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.repository.CategoryRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final CategoryFactory categoryFactory = mock(CategoryFactory.class);
    private final CategoryService underTest = new CategoryService(categoryRepository, categoryFactory);
    private final String categoryId = "1";

    @Test
    public void shouldReturnCategoryDto() {
        CategoryDto categoryDto = new CategoryDto("id", "title", "thumbnailUrl", "description");
        Category category = new Category("id", "title", "thumbnailUrl", "description");

        when(categoryRepository.findByCategoryId(categoryId)).thenReturn(Optional.of(category));
        when(categoryFactory.convertToDto(category)).thenReturn(categoryDto);

        Optional<CategoryDto> result = underTest.findBy(categoryId);

        assertThat(result, is(Optional.of(categoryDto)));
    }

    @Test
    public void shouldReturnOptionalEmptyIfIdNotFound() {
        when(categoryRepository.findByCategoryId(categoryId)).thenReturn(Optional.empty());
        assertThat(underTest.findBy(categoryId), is(Optional.empty()));
    }

    @Test
    public void shouldReturnSetOfAllCategories() {
        Category category1 =new Category("1", "title", "thumbnailUrl", "description");
        Category category2 =new Category("2", "title", "thumbnailUrl", "description");
        Category category3 =new Category("3", "title", "thumbnailUrl", "description");
        List<Category> categories = List.of(
                category1,
                category2,
                category3
        );

        CategoryDto categoryDto1 =new CategoryDto("1", "title", "thumbnailUrl", "description");
        CategoryDto categoryDto2 =new CategoryDto("2", "title", "thumbnailUrl", "description");
        CategoryDto categoryDto3 =new CategoryDto("3", "title", "thumbnailUrl", "description");
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