package me.jcala.xmarket.server.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(//配置Mongo repository 的扫描位置
        basePackages = {"me.jcala.xmarket.server.repository",
                "me.jcala.xmarket.server.admin.repository"}
)
public interface MongoConfig {
}
