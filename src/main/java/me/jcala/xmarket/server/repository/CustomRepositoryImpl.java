package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.document.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class CustomRepositoryImpl implements CustomRepository{
    private MongoTemplate template;

    @Autowired
    public CustomRepositoryImpl(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public void updateUserSchool(String username, String school) throws RuntimeException{
        template.updateFirst(new Query(where("username").is(username)),
                new Update().set("school",school), User.class);
    }

    @Override
    public void updateUserPassword(String username,String password) throws RuntimeException {
       template.updateFirst(new Query(where("username").is(username)),
               new Update().set("password",password), User.class);
    }
}
