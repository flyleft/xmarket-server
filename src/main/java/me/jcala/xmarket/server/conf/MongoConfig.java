package me.jcala.xmarket.server.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "me.jcala.xmarket.pre.repository")
@ComponentScan
public interface MongoConfig {

}
