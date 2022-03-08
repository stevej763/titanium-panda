package com.titaniumpanda.app.repository;

import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
abstract public class AbstractMongoRepositoryTest {

    @Autowired
    Environment environment;

    protected static String testDatabaseName = "test";
    protected static String collectionName ="photo";
    protected static MongoTemplate mongoTemplate;

    @BeforeEach
    void setUpDatabase() {
        String hostname = environment.getProperty("spring.data.mongodb.host");
        String port = environment.getProperty("spring.data.mongodb.port");
        mongoTemplate = new MongoTemplate(MongoClients.create("mongodb://" + hostname + ":" + port), testDatabaseName);
    }

    @AfterEach
    void cleanDatabase() {
        mongoTemplate.dropCollection(collectionName);
    }
}
