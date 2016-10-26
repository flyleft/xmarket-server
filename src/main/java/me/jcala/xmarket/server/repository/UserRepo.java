package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.document.User;
import me.jcala.xmarket.server.entity.dto.Result;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {
    private Datastore datastore;

    @Autowired
    public UserRepo(Datastore datastore) {
        this.datastore = datastore;
    }

    public Result<String> register(){
        final User user=new User();
        user.setSchool("123");
        user.setUsername("23332");
        user.setPhone("233");
        datastore.save(user);
        return null;
    }
}
