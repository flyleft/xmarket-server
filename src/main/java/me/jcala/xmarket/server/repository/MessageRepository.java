package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.document.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message,String> {

    Page<Message> findByBelongId(String belongId,Pageable pageable);
}
