package me.jcala.xmarket.pre.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "me.jcala.xmarket.pre.repository")
public interface MongoConfig {

}
