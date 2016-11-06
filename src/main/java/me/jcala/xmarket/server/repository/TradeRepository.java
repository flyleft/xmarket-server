package me.jcala.xmarket.server.repository;


import me.jcala.xmarket.server.entity.document.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends MongoRepository<Trade,String>{

    @SuppressWarnings("unchecked")
    @Query(fields =  "{'_id':1}")
    @Override
    Trade save(Trade trade);
}
