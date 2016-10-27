package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.document.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class CustomRepository {
    private MongoTemplate template;

    @Autowired
    public CustomRepository(MongoTemplate template) {
        this.template = template;
    }
    /*
     mongoTemplate.updateMulti(new Query(where("accounts.accountType").is(Account.Type.SAVINGS)),
  new Update().inc("accounts.$.balance", 50.00), Account.class);
     */
    public void updateUserSchool(String username, String school){
        template.updateFirst(new Query(where("username").is(username)),
                new Update().set("school",school), User.class);
    }
}
