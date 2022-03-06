package com.titaniumpanda.app.repository;

import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

@DataMongoTest
@TestPropertySource(locations = "classpath:test.properties")
abstract public class AbstractMongoRepositoryTest {

    protected static String testDatabaseName = "test";
    protected static String collectionName ="photo";
    protected static MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate = new MongoTemplate(MongoClients.create("mongodb://jenkins-mongo:27017"), testDatabaseName);
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(collectionName);
    }
}
