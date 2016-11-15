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
    public void updateUserPhoneSchool(String userId,String phone, String school){
        template.updateFirst(new Query(where("_id").is(userId)),
                new Update().set("school",school), User.class);

        template.updateFirst(new Query(where("_id").is(userId)),
                new Update().set("phone",phone), User.class);
    }

    @Override
    public void updateUserPassword(String userId,String password){
       template.updateFirst(new Query(where("_id").is(userId)),
               new Update().set("password",password), User.class);
    }

    @Override
    public void updateUserAvatar(String userId, String avatarUrl) {
        template.updateFirst(new Query(where("_id").is(userId)),
                new Update().set("avatar_url",avatarUrl), User.class);
    }

    @Override
    public void updateUserTrades(String which_col,String userId, String trade_id) {
        template.updateFirst(new Query(where("_id").is(userId)),
                new Update().push(which_col,trade_id), User.class);
    }

    @Override
    public void updateUserTeams(String userId, String teamId) {
        template.updateFirst(new Query(where("_id").is(userId)),
                new Update().push("teams",teamId), User.class);
    }
}
