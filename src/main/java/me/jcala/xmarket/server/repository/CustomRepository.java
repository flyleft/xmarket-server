package me.jcala.xmarket.server.repository;


/**
 * 通过MongoTemplate实现MongoRepository难以实现的操作
 */
public interface CustomRepository {
    void updateUserSchool(String user_id, String school);//通过username更新所在学校的信息
    void updateUserPassword(String user_id,String password);//修改用户密码
    void updateUserAvatar(String user_id,String avatar_url);//更新用户的头像
    void updateUserTrades(String which_col, String user_id, String trade_id);//更新用户待售商品列
}
