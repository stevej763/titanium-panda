package com.titaniumpanda.app.webtest;

import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractWebTest {

    protected static final String testDatabaseName = "test";

    protected static String collectionName = "test";
    protected static MongoTemplate mongoTemplate;

    @LocalServerPort
    protected int port;

    @Autowired
    private Environment environment;

    @Autowired
    protected TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        String hostname = environment.getProperty("spring.data.mongodb.host");
        String port = environment.getProperty("spring.data.mongodb.port");
        mongoTemplate = new MongoTemplate(MongoClients.create("mongodb://" + hostname+ ":" + port), testDatabaseName);
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(collectionName);
    }
}
