package com.example.titaniumpanda.api.photos;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.example.titaniumpanda.api.photos.ResourceErrorMessage.RESOURCE_NOT_FOUND;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ExceptionHandlerControllerTest {

    @Test
    public void shouldReturnErrorMessageWhenResourceNotFound() {
        ExceptionHandlerController underTest = new ExceptionHandlerController();
        ResourceErrorMessage expected = new ResourceErrorMessage(NOT_FOUND.value(), RESOURCE_NOT_FOUND);
        assertThat(underTest.requestHandlingNoHandlerFound().getBody(), is(expected));
        assertThat(underTest.requestHandlingNoHandlerFound().getStatusCode(), is(NOT_FOUND));
    }
}