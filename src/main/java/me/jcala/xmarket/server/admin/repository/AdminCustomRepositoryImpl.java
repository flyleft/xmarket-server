package me.jcala.xmarket.server.admin.repository;

import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.entity.TradeTag;
import me.jcala.xmarket.server.admin.init.SysColName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class AdminCustomRepositoryImpl implements AdminCustomRepository{

    private MongoTemplate template;

    @Autowired
    public AdminCustomRepositoryImpl(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public void addTradeTag(TradeTag tradeTag) {
        template.updateFirst(new Query(where("name").is(SysColName.colTradeTag.name())),
                new Update().pull("tradeTags",tradeTag), SystemBean.class);

    }

}
