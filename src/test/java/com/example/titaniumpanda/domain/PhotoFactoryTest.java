package com.example.titaniumpanda.domain;

import com.example.titaniumpanda.api.photos.PhotoDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

public class PhotoFactoryTest {

    @Test
    public void shouldConvertToDto() {
        PhotoFactory underTest = new PhotoFactory();
        Photo photo = new Photo("title", "id", "photoUrl", "description");
        PhotoDto expected = new PhotoDto("title", "id", "photoUrl", "description");
        assertThat(underTest.convertToDto(photo), is(expected));
    }

}