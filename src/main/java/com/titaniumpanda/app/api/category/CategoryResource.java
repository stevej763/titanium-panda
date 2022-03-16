package com.titaniumpanda.app.api.category;

import com.titaniumpanda.app.domain.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.titaniumpanda.app.api.category.CategoryResource.CATEGORY_RESOURCE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(CATEGORY_RESOURCE_URL)
public class CategoryResource {

    public static final String CATEGORY_RESOURCE_URL = "api/category";

    @Autowired
    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/id/{categoryId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") UUID categoryId) {
        Optional<CategoryDto> categoryDto = categoryService.findById(categoryId);
        if (categoryDto.isPresent()) {
            return ResponseEntity.ok(categoryDto.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = "/delete/{categoryId}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("categoryId") UUID categoryId) {
        boolean result = categoryService.deleteCategory(categoryId);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/name/{categoryName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable("categoryName") String categoryName) {
        Optional<CategoryDto> categoryDto = categoryService.findByName(categoryName);
        if (categoryDto.isPresent()) {
            return ResponseEntity.ok(categoryDto.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        return ResponseEntity.ok().body(categories);
    }

    @PostMapping(value = "/upload", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CategoryDto> addCategory(CategoryRequest categoryRequest) {
        Optional<CategoryDto> response = categoryService.save(categoryRequest);
        if (response.isPresent()) {
            CategoryDto categoryDto = response.get();
            String resourceLocation = CATEGORY_RESOURCE_URL + "/" + categoryDto.getCategoryId();
            return ResponseEntity.created(URI.create(resourceLocation)).body(categoryDto);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/update", consumes = { MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CategoryDto> updateCategory(CategoryUpdateRequest categoryUpdateRequest) {
        Optional<CategoryDto> response = categoryService.update(categoryUpdateRequest);
        if (response.isPresent()) {
            CategoryDto updatedCategory = response.get();
            return ResponseEntity.ok().body(updatedCategory);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
