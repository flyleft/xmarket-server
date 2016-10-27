package me.jcala.xmarket.server.repository;


/**
 * 通过MongoTemplate实现MongoRepository难以实现的操作
 */
public interface CustomRepository {
    void updateUserSchool(String username, String school) throws RuntimeException;//通过username更新所在学校的信息

}
