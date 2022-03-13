package com.titaniumpanda.app.integration.repository;

import com.titaniumpanda.app.domain.Photo;
import com.titaniumpanda.app.domain.ids.CategoryId;
import com.titaniumpanda.app.domain.ids.PhotoId;
import com.titaniumpanda.app.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoRepositoryTest extends AbstractMongoRepositoryTest {

    private static final CategoryId CATEGORY_ID = new CategoryId();
    private static final String PHOTO_BASE_URL = "baseUrl";
    private static final LocalDateTime CREATED_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MODIFIED_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final String PHOTO_DESCRIPTION = "PhotoRepositoryTest";
    private static final String PHOTO_THUMBNAIL_URL = "photoUrl";
    private static final String TITLE = "title";
    private final PhotoId photoId1 = new PhotoId();

    @Autowired
    PhotoRepository photoRepository;

    @Test
    public void shouldFindPhotoById() {
        Photo photo = new Photo(photoId1, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, List.of(CATEGORY_ID));
        mongoTestTemplate.save(photo);

        Optional<Photo> result = photoRepository.findById(photoId1);

        assertThat(result, is(Optional.of(photo)));
    }

    @Test
    public void shouldReturnEmptyOptionalWhenNoMatchingResultById() {
        Optional<Photo> result = photoRepository.findById(photoId1);

        assertThat(result, is(Optional.empty()));
    }

    @Test
    public void shouldFindListOfPhotos() {
        PhotoId photoId2 = new PhotoId();
        PhotoId photoId3 = new PhotoId();
        Photo photo1 = new Photo(photoId1, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, List.of(CATEGORY_ID));
        Photo photo2 = new Photo(photoId2, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, List.of(CATEGORY_ID));
        Photo photo3 = new Photo(photoId3, TITLE, PHOTO_THUMBNAIL_URL, PHOTO_DESCRIPTION, CREATED_DATE_TIME, MODIFIED_DATE_TIME, PHOTO_BASE_URL, List.of(CATEGORY_ID));

        mongoTestTemplate.save(photo1);
        mongoTestTemplate.save(photo2);
        mongoTestTemplate.save(photo3);

        List<Photo> result = photoRepository.findAll();

        List<Photo> expected = List.of(photo1, photo2, photo3);

        assertThat(result, is(expected));
    }

    @Test
    public void shouldReturnEmptyListWhenNoPhotoFound() {
        List<Photo> result = photoRepository.findAll();
        assertThat(result, is(emptyList()));
    }
}