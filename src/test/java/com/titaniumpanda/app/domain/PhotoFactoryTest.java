package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.domain.ids.CategoryId;
import com.titaniumpanda.app.domain.ids.PhotoId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoFactoryTest {

    public static final PhotoId PHOTO_ID = new PhotoId();
    private final LocalDateTime now = LocalDateTime.now();
    private final String photoBaseUrl = "photoBaseUrl";
    private final List<CategoryId> categories = List.of(new CategoryId());

    @Test
    public void shouldConvertToDto() {
        PhotoFactory underTest = new PhotoFactory();
        Photo photo = new Photo(PHOTO_ID, "title", "photoUrl", "description", now, now, photoBaseUrl, categories);
        PhotoDto expected = new PhotoDto(PHOTO_ID, "title", "photoUrl", "description", now, now, photoBaseUrl, categories);
        assertThat(underTest.convertToDto(photo), is(expected));
    }

}