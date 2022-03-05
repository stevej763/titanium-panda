package com.example.titaniumpanda.api.photos;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

public class PhotoResourceTest {

    @Test
    public void shouldReturnPhoto() {
        PhotoResource underTest = new PhotoResource();

        assertThat(underTest.getPhoto().getBody(), is(new PhotoDto("photo title")));
    }
}