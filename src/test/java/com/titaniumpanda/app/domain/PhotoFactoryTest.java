package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.photo.PhotoDto;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoFactoryTest {

    @Test
    public void shouldConvertToDto() {
        PhotoFactory underTest = new PhotoFactory();
        Photo photo = new Photo("title", "id", "photoUrl", "description");
        PhotoDto expected = new PhotoDto("title", "id", "photoUrl", "description");
        assertThat(underTest.convertToDto(photo), is(expected));
    }

}