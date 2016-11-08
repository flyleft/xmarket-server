package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.document.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends MongoRepository<Team,String>{

    @Query(value = "{ 'schoolName' : ?0 }")
    List<Team> findAllBySchoolName(String schoolName);
}
