package com.titaniumpanda.app.api.photos;

import com.titaniumpanda.app.api.fourzerofour.ExceptionHandlerController;
import com.titaniumpanda.app.api.fourzerofour.ResourceErrorMessage;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ExceptionHandlerControllerTest {

    @Test
    public void shouldReturnErrorMessageWhenResourceNotFound() {
        ExceptionHandlerController underTest = new ExceptionHandlerController();
        ResourceErrorMessage expected = new ResourceErrorMessage(NOT_FOUND.value(), ResourceErrorMessage.RESOURCE_NOT_FOUND);
        assertThat(underTest.requestHandlingNoHandlerFound().getBody(), is(expected));
        assertThat(underTest.requestHandlingNoHandlerFound().getStatusCode(), is(NOT_FOUND));
    }
}