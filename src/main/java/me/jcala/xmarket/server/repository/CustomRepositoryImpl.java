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
    public void updateUserSchool(String user_id, String school) throws RuntimeException{
        template.updateFirst(new Query(where("_id").is(user_id)),
                new Update().set("school",school), User.class);
    }

    @Override
    public void updateUserPassword(String user_id,String password) throws RuntimeException {
       template.updateFirst(new Query(where("_id").is(user_id)),
               new Update().set("password",password), User.class);
    }

    @Override
    public void updateUserAvatar(String user_id, String avatar_url) {
        template.updateFirst(new Query(where("_id").is(user_id)),
                new Update().set("avatar_url",avatar_url), User.class);
    }

    @Override
    public void updateUserTrades(String which_col,String user_id, String trade_id) {
        template.updateFirst(new Query(where("_id").is(user_id)),
                new Update().push(which_col,trade_id), User.class);
    }

    @Override
    public void updateUserTeams(String user_id, String team_id) {
        template.updateFirst(new Query(where("_id").is(user_id)),
                new Update().push("teams",team_id), User.class);
    }
}
