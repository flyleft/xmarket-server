package me.jcala.xmarket.server.repository;


import me.jcala.xmarket.server.entity.document.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository extends MongoRepository<Trade,String>{


    List<Trade> findBySchoolName(String schoolName);


    @Query(value = "{ 'tagName' : ?0 }")
    List<Trade> findByTagName(String tagName);

    Trade findById(String id);

    @Query(value = "{ 'id' : ?0 }", fields = "{ 'id': 1, 'imgUrls': 1,'author':1}")
    Trade findPartsById(String id);

}
