package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    long countUserByUsername(String name);


    long countByUsernameAndPassword(@Param("username") String name,
                                   @Param("password") String pass);

    long countByPhone(String phone);

}
