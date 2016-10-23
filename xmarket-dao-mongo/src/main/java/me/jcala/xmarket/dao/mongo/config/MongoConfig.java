package me.jcala.xmarket.dao.mongo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "me.jcala.xmarket.dao.mongo.repository")
@ComponentScan
public interface MongoConfig {

}
