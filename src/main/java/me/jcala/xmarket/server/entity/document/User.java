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
    private List<String> sellTrades;//用户在售商品id列表
    private List<String> soldTrades;//用户已售商品id列表
    private List<String> donateTrades;//捐赠商品id列表
    private List<String> boughtTrades;//已买到商品id列表
    private List<String> toBeConfirmTrades;//待确认商品id列表
}
