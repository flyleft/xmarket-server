package me.jcala.xmarket.server.admin.repository;

import me.jcala.xmarket.server.admin.entity.SystemBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemRepository extends MongoRepository<SystemBean,String>{

    Optional<SystemBean> findByName(String name);
}
