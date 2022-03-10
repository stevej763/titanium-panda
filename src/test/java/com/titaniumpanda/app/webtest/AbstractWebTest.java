package com.titaniumpanda.app.webtest;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public abstract class AbstractWebTest {

    protected static String collectionName = "test";

    @LocalServerPort
    protected int port;

    @Autowired
    private Environment environment;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(collectionName);
    }
}
