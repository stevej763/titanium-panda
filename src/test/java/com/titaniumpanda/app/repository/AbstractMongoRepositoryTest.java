package com.titaniumpanda.app.repository;

import com.titaniumpanda.app.TestMongoDbConfiguration;
import com.titaniumpanda.app.TitaniumPandaApplication;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {TitaniumPandaApplication.class, TestMongoDbConfiguration.class})
@ActiveProfiles("test")
public abstract class AbstractMongoRepositoryTest {

    @Autowired
    protected MongoTemplate mongoTestTemplate;

    protected static final String collectionName ="photo";

    @AfterEach
    void cleanDatabase() {
        mongoTestTemplate.dropCollection(collectionName);
    }
}
