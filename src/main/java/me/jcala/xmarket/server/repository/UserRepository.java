package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    long countById(String id);

    long countByUsername(String username);

    long countByIdAndPassword(@Param("id") String id,@Param("password") String pass);

    Optional<User> findByUsernameAndPassword(@Param("username") String username,@Param("password") String pass);

    long countByPhone(String phone);

    @SuppressWarnings("unchecked")
    User save(User user);
}
