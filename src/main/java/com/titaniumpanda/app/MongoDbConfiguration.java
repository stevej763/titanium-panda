package com.titaniumpanda.app;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDbConfiguration {

    @Autowired
    Environment environment;

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), environment.getProperty("mongodb.database"));
    }

    @Bean
    public MongoTemplate mongoTestTemplate() {
        return new MongoTemplate(mongoClient(), environment.getProperty("mongodb.testDatabase"));
    }

    public MongoClient mongoClient() {
        ConnectionString connectionString = getConnectionString();
        MongoClientSettings mongoClientSettings = mongoClientSettings(connectionString);

        return MongoClients.create(mongoClientSettings);
    }

    private MongoClientSettings mongoClientSettings(ConnectionString connectionString) {
        return MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
    }

    private ConnectionString getConnectionString() {
        String hostname = environment.getProperty("mongodb.host");
        String port = environment.getProperty("mongodb.port");
        return new ConnectionString("mongodb://" + hostname + ":" + port);
    }

}