package com.titaniumpanda.app.repository;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public abstract class AbstractMongoRepositoryTest {

    @Autowired
    protected MongoTemplate mongoTemplate;

    protected static final String collectionName ="photo";

    @AfterEach
    void cleanDatabase() {
        mongoTemplate.dropCollection(collectionName);
    }
}
