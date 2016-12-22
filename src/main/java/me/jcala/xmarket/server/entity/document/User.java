package me.jcala.xmarket.server.entity.document;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
    private String username;//用户名U
    private String password;//用户密码
    private String school;//所在学校名称
    private String phone;//手机号
    private String avatarUrl;//头像地址
    private List<String> sellTrades;//用户在售商品id列表
    private List<String> soldTrades;//用户已售商品id列表
    private List<String> donateTrades;//捐赠商品id列表
    private List<String> boughtTrades;//已买到商品id列表
    private List<String> toBeConfirmTrades;//待确认商品id列表
    private List<String> teams;//志愿队的id列表
}
