package me.jcala.xmarket.ser.conf;

import me.jcala.xmarket.mongo.config.MongoConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MongoConfig.class)
@ComponentScan("me.jcala.xmarket.ser.service")
public interface ServiceConfig {
}
