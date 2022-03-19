package com.titaniumpanda.app.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoBuilderTest {

    @Test
    public void shouldCreatePhotoFromRequest() {

        PhotoBuilder underTest = new PhotoBuilder();

        LocalDateTime createdDateTime = LocalDateTime.now();
        UUID photoId = UUID.randomUUID();
        UUID uploadId = UUID.randomUUID();
        String title = "title";
        String description = "description";
        Photo result = underTest.setPhotoId(photoId)
                .setTitle(title)
                .setUploadId(uploadId)
                .setPhotoDescription(description)
                .setCreatedDateTime(createdDateTime)
                .setCategories(emptyList())
                .build();

        Photo expected = new Photo(photoId, title, uploadId, description, createdDateTime, createdDateTime, emptyList());

        assertThat(result, is(expected));
    }

}