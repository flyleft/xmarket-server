package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    long countByUsername(String username);

    long countByIdAndPassword(@Param("id") String id,@Param("password") String pass);

    long countByUernameAndPassword(@Param("username") String username,@Param("password") String pass);

    long countByPhone(String phone);

    @Query(value = "{ }", fields = "{ }")
    User save(User user);

    Optional<User> findByUsername(String username);
}
