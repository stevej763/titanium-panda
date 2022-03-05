package com.example.titaniumpanda.domain;

import com.example.titaniumpanda.api.photos.PhotoDto;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoServiceTest {

    @Test
    public void shouldReturnPhotoDto() {
        String id = "1";
        PhotoDto photoDto = new PhotoDto("photo title", id);
        PhotoService underTest = new PhotoService();
        assertThat(underTest.findPhotoBy(id), is(Optional.of(photoDto)));
    }


    @Test
    public void shouldReturnOptionalEmptyIfIdNotFound() {
        String id = "100";
        PhotoService underTest = new PhotoService();
        assertThat(underTest.findPhotoBy(id), is(Optional.empty()));
    }
}