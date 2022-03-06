package com.titaniumpanda.app.webtest;

import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public abstract class AbstractWebTest {

    protected static String testDatabaseName = "test";
    protected static String collectionName ="photo";
    protected static MongoTemplate mongoTemplate;

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate = new MongoTemplate(MongoClients.create("mongodb://172.17.0.4:27018"), testDatabaseName);
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(collectionName);
    }
}
