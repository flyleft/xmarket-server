package me.jcala.xmarket.server.repository;


/**
 * 通过MongoTemplate实现MongoRepository难以实现的操作
 */
public interface CustomRepository {
    void updateUserPhoneSchool(String userId, String phone,String school);//通过username更新所在学校的信息
    void updateUserPassword(String userId,String password);//修改用户密码
    void updateUserAvatar(String userId,String avatar_url);//更新用户的头像
    void addToUserTrades(String whichCol, String userId, String tradeId);//用户待售商品列数组加入数据
    void deleteFromUserTrades(String whichCol, String userId, String tradeId);//用户待售商品列数组删除数据
    void updateTradeStatus(String tradeId,int status);//修改商品的status
    void updateUserTeams(String userId,String team_id);//更新用户的志愿队id列表
    void addToTeamTrades(String team,String tradeId);//tradeId加入志愿队的商品列表
}
