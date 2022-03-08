package com.titaniumpanda.app.webtest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ResourceNotFoundWebTest extends AbstractWebTest {

    @Test
    public void shouldReturnFourZeroFour() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/" + UUID.randomUUID(), String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(responseEntity.getBody(), is("{\"statusCode\":405,\"errorMessage\":\"Resource not found\"}"));
    }
}
