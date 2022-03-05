package com.titaniumpanda.app.webtest;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class HealthCheckResourceWebTest extends AbstractWebTest {

	@Test
	public void pingShouldReturnPong() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/ping", String.class);
		assertThat(responseEntity.getBody(), is("pong"));
	}
}
