package com.titaniumpanda.app.webtest;

import com.titaniumpanda.app.TestMongoDbConfiguration;
import com.titaniumpanda.app.TitaniumPandaApplication;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TitaniumPandaApplication.class, TestMongoDbConfiguration.class})
public abstract class AbstractWebTest {

    protected static String collectionName = "photo";

    @LocalServerPort
    protected int port;

    @Autowired
    private Environment environment;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected MongoTemplate mongoTestTemplate;

    @AfterEach
    void tearDown() {
        mongoTestTemplate.dropCollection(collectionName);
    }
}
