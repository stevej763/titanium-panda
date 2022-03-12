package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.domain.ids.PhotoId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoBuilderTest {

    @Test
    public void shouldCreatePhotoFromRequest() {

        PhotoBuilder underTest = new PhotoBuilder();

        LocalDateTime createdDateTime = LocalDateTime.now();
        PhotoId photoId = new PhotoId();
        String title = "title";
        String thumbnail = "thumbnail";
        String description = "description";
        String url_base = "url base";
        Photo result = underTest.setPhotoId(photoId)
                .setTitle(title)
                .setPhotoThumbnail(thumbnail)
                .setPhotoDescription(description)
                .setCreatedDateTime(createdDateTime)
                .setPhotoBaseUrl(url_base)
                .setCategories(emptyList())
                .build();

        Photo expected = new Photo(photoId, title, thumbnail, description, createdDateTime, createdDateTime, url_base, emptyList());

        assertThat(result, is(expected));
    }

}