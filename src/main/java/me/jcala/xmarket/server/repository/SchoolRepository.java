package me.jcala.xmarket.server.repository;

import me.jcala.xmarket.server.entity.dao.School;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.stream.Stream;

@Repository
public interface SchoolRepository extends MongoRepository<School,String>{

    Stream<School> streamAll();//获取所有school名字列表
}
