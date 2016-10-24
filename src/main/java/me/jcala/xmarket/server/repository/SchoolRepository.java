package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.dao.School;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends MongoRepository<School,String>{
}
