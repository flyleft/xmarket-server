package me.jcala.xmarket.server.repository;


import me.jcala.xmarket.server.entity.document.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends MongoRepository<Trade,String>{

    @SuppressWarnings("unchecked")
    @Query(fields =  "{'_id':1}")
    @Override
    Trade save(Trade trade);

    @Query(value = "{ 'schoolName' : ?0 }")
    List<Trade> findBySchoolName(String schoolName);


    @Query(value = "{ 'tagId' : ?0 }")
    List<Trade> findByTagId(String tagId);

}
