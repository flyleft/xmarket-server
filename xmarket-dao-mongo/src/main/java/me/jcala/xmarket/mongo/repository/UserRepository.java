package me.jcala.xmarket.mongo.repository;

import me.jcala.xmarket.mongo.dao.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{'username': ?0}", count = true)
    User findUserByname(@Param("username") String name);

}
