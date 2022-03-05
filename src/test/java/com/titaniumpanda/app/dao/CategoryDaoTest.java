package com.titaniumpanda.app.dao;

import com.titaniumpanda.app.domain.Category;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CategoryDaoTest {

    private final CategoryDao underTest = new CategoryDao();

    @Test
    public void shouldReturnCategory() {
        Category category = new Category("1", "title", "thumbnailUrl", "description");
        Optional<Category> result = underTest.findById("1");

        assertThat(result, is(Optional.of(category)));
    }

    @Test
    public void shouldReturnAllCategories() {
        assertThat(underTest.findAll(), is(underTest.fakeDb));
    }
}