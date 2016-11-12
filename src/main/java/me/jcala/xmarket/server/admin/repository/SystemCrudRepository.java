package me.jcala.xmarket.server.admin.repository;

import me.jcala.xmarket.server.admin.entity.SystemBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemCrudRepository extends MongoRepository<SystemBean,String>{

    SystemBean findByName(String name);
}
