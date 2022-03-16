package com.titaniumpanda.app.integration.repository;

import com.titaniumpanda.app.domain.Category;
import com.titaniumpanda.app.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CategoryRepositoryTest extends AbstractMongoRepositoryTest {

    private final UUID categoryId = UUID.randomUUID();
    private static final LocalDateTime CREATED_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MODIFIED_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private final String title = "title";
    private final String description = "description";
    private final Category category = new Category(categoryId, title, description, CREATED_DATE_TIME, MODIFIED_DATE_TIME);

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeAll
    static void beforeAll() {
        collectionName = "category";
    }

    @Test
    public void shouldFindCategoryById() {
        mongoTestTemplate.save(category);

        Optional<Category> result = categoryRepository.findById(categoryId);

        assertThat(result, is(Optional.of(category)));
    }

    @Test
    public void shouldFindCategoryByName() {
        Category category = new Category(categoryId, title, description, CREATED_DATE_TIME, MODIFIED_DATE_TIME);
        mongoTestTemplate.save(category);

        Optional<Category> result = categoryRepository.findByCategoryName(title);

        assertThat(result, is(Optional.of(category)));
    }

    @Test
    public void shouldUpdateCategory() {
        Category updatedCategory = new Category(categoryId, "new name", "new description", CREATED_DATE_TIME, MODIFIED_DATE_TIME);
        mongoTestTemplate.save(category);
        categoryRepository.save(updatedCategory);

        Category result = mongoTestTemplate.findById(categoryId, Category.class);

        assertThat(result, is(updatedCategory));
    }

    @Test
    public void shouldDeleteCategory() {
        Category category = new Category(categoryId, title, description, CREATED_DATE_TIME, MODIFIED_DATE_TIME);
        mongoTestTemplate.save(category);
        categoryRepository.delete(category);

        Optional<Category> result = Optional.ofNullable(mongoTestTemplate.findById(categoryId, Category.class));

        assertThat(result, is(Optional.empty()));
    }
}