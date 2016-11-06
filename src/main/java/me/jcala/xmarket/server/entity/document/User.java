package me.jcala.xmarket.server.entity.document;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 *用户信息封装类
 */
@Document(collection = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String username;//用户名
    private String password;//用户密码
    private String school;//所在学校名称
    private String phone;//手机号
    private String avatar_url;//头像地址
    private List<String> sell_trades;//用户在售商品id列表
    private List<String> sold_trades;//用户已售商品id列表
    private List<String> donate_trades;//捐赠商品id列表
    private List<String> bought_trades;//已买到商品id列表
    private List<String> to_be_confirm_trades;//待确认商品id列表
}
