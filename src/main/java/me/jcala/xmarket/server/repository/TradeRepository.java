package me.jcala.xmarket.server.repository;


import me.jcala.xmarket.server.entity.document.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository extends MongoRepository<Trade,String>{

    @Query(fields = "{ 'id': 1, 'imgUrls': 1,'author':1}")
    List<Trade> findBySchoolNameAndStatus(String schoolName,int status);


    @Query(fields = "{ 'id': 1, 'imgUrls': 1,'author':1}")
    List<Trade> findByTagNameAndStatus(String tagName,int status);

    Trade findById(String id);

    @Query(value = "{ 'id' : ?0 }", fields = "{ 'id': 1, 'imgUrls': 1,'author':1}")
    Trade findPartsById(String id);

}
