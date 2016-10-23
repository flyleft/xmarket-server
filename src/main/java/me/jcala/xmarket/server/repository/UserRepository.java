package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.dao.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{'username': ?0}", count = true)
    User findUserByname(@Param("username") String name);

    @Query(value = "'username': ?0")
    User findByUsernameAndPassword(@Param("username") String name,
                                   @Param("password") String pass);

}
