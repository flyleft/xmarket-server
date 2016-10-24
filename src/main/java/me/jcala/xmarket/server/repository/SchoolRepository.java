package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.dao.School;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SchoolRepository extends MongoRepository<School,String>{
}
