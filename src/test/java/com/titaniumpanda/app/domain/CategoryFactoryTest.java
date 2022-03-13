package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.category.CategoryDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryFactoryTest {

    private final UUID categoryId = UUID.randomUUID();

    @Test
    public void shouldConvertToCategoryDto() {
        CategoryFactory underTest = new CategoryFactory();
        Category category = new Category(categoryId, "1", "1", "1");
        CategoryDto categoryDto = new CategoryDto(categoryId, "1", "1", "1");

        assertThat(underTest.convertToDto(category), Is.is(categoryDto));
    }

}