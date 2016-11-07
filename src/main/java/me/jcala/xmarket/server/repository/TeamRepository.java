package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.document.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends MongoRepository<Team,String>{
}
