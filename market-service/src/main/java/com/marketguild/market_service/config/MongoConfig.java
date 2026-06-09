package com.marketguild.market_service.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        String uri = System.getenv("SPRING_DATA_MONGODB_URI");
        if (uri == null) {
            uri = "mongodb://mongodb:27017/marketdb";
        }
        return MongoClients.create(uri);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "marketdb");
    }
}