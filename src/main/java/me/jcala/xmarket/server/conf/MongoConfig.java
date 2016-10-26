package me.jcala.xmarket.server.conf;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"me.jcala.xmarket.server.repository",
        "me.jcala.xmarket.server.admin.repository"})
public class MongoConfig {

    @Bean
   public Datastore datastore(){
       final Morphia morphia = new Morphia();
       morphia.mapPackage("me.jcala.xmarket.server.entity.document");
       final Datastore datastore = morphia.createDatastore(new MongoClient(), "xmarket");
       datastore.ensureIndexes();
       return datastore;
   }
}
