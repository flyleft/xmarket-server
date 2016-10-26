package me.jcala.xmarket.server.admin.repository;

import me.jcala.xmarket.server.entity.document.admin.System;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRepository extends MongoRepository<System,String>{

    System findByName(String name);


}
