package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import com.titaniumpanda.app.api.category.CategoryRequest;
import com.titaniumpanda.app.api.category.CategoryUpdateRequest;
import com.titaniumpanda.app.repository.CategoryRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final CategoryFactory categoryFactory = mock(CategoryFactory.class);

    private final UUID categoryId = UUID.randomUUID();
    private final LocalDateTime createdDateTime = LocalDateTime.now();
    private final LocalDateTime modifiedDateTime = LocalDateTime.now();
    private final String categoryName = "name";
    private final String categoryDescription = "description";

    private final CategoryService underTest = new CategoryService(categoryRepository, categoryFactory);

    @Test
    public void shouldReturnCategoryDtoFromId() {
        CategoryDto categoryDto = new CategoryDto(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
        Category category = new Category(UUID.randomUUID(), categoryName, categoryDescription, createdDateTime, modifiedDateTime);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryFactory.convertToDto(category)).thenReturn(categoryDto);

        Optional<CategoryDto> result = underTest.findById(categoryId);

        assertThat(result, is(Optional.of(categoryDto)));
    }

    @Test
    public void shouldReturnCategoryDtoFromName() {
        CategoryDto categoryDto = new CategoryDto(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
        Category category = new Category(UUID.randomUUID(), categoryName, categoryDescription, createdDateTime, modifiedDateTime);

        when(categoryRepository.findByCategoryName(categoryName)).thenReturn(Optional.of(category));
        when(categoryFactory.convertToDto(category)).thenReturn(categoryDto);

        Optional<CategoryDto> result = underTest.findByName(categoryName);

        assertThat(result, is(Optional.of(categoryDto)));
    }

    @Test
    public void shouldReturnOptionalEmptyIfIdNotFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        assertThat(underTest.findById(categoryId), is(Optional.empty()));
    }

    @Test
    public void shouldReturnSetOfAllCategories() {
        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();
        UUID categoryId3 = UUID.randomUUID();
        Category category1 =new Category(categoryId1, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
        Category category2 =new Category(categoryId2, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
        Category category3 =new Category(categoryId3, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
        List<Category> categories = List.of(
                category1,
                category2,
                category3
        );

        CategoryDto categoryDto1 =new CategoryDto(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
        CategoryDto categoryDto2 =new CategoryDto(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
        CategoryDto categoryDto3 =new CategoryDto(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
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

    @Test
    public void shouldReturnCategoryDtoOnSave() {
        CategoryRequest categoryRequest = new CategoryRequest(categoryName, categoryDescription);
        CategoryDto categoryDto = new CategoryDto(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
        Category category = new Category(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);

        when(categoryFactory.createNewCategory(categoryRequest)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryFactory.convertToDto(category)).thenReturn(categoryDto);

        Optional<CategoryDto> result = underTest.save(categoryRequest);

        assertThat(result, is(Optional.of(categoryDto)));
    }

    @Test
    public void shouldReturnCategoryDtoOnSuccessfulUpdate() {
        String newDescription = "new description";
        String newName = "new name";
        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest(categoryId.toString(), newName, newDescription);
        CategoryDto categoryDto = new CategoryDto(categoryId, newName, newDescription, createdDateTime, modifiedDateTime);
        Category category = new Category(categoryId, categoryName, categoryDescription, createdDateTime, modifiedDateTime);
        Category updatedCategory = new Category(categoryId, newName, newDescription, createdDateTime, modifiedDateTime);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryFactory.updateCategory(category, categoryUpdateRequest)).thenReturn(updatedCategory);
        when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);
        when(categoryFactory.convertToDto(updatedCategory)).thenReturn(categoryDto);

        Optional<CategoryDto> result = underTest.update(categoryUpdateRequest);

        assertThat(result, is(Optional.of(categoryDto)));
    }

    @Test
    public void shouldReturnEmptyOptionalWhenCategoryToUpdateDoesNotExist() {
        String newDescription = "new description";
        String newName = "new name";
        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest(categoryId.toString(), newName, newDescription);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Optional<CategoryDto> result = underTest.update(categoryUpdateRequest);

        assertThat(result, is(Optional.empty()));
    }

    @Test
    public void shouldReturnTrueIfCategorySuccessfullyDeleted() {
        Category category = new Category(UUID.randomUUID(), categoryName, categoryDescription, createdDateTime, modifiedDateTime);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Boolean result = underTest.deleteCategory(categoryId);

        verify(categoryRepository).delete(category);
        assertThat(result, is(true));
    }

    @Test
    public void shouldReturnFalseIfCategoryNotFound() {
        Category category = new Category(UUID.randomUUID(), categoryName, categoryDescription, createdDateTime, modifiedDateTime);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Boolean result = underTest.deleteCategory(categoryId);

        verify(categoryRepository, never()).delete(category);
        assertThat(result, is(false));
    }
}