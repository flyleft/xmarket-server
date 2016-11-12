package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.admin.entity.SystemBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemGetRepository extends MongoRepository<SystemBean,String> {
    SystemBean findByName(String name);
}
