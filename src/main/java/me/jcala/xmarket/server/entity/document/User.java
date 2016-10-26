package me.jcala.xmarket.server.entity.document;


import lombok.*;
import org.mongodb.morphia.annotations.*;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 *用户信息封装类
 */
@Entity("user")
@Document(collection = "user")
@Getter
@Setter
@NoArgsConstructor
@Indexes(
        @Index(value = "username", fields = @Field("username"))
)
public class User {
    @Id
    private String username;//用户名
    private String password;//用户密码
    private String school;//所在学校名称
    private String phone;//手机号
    private String avatar_url;//头像地址
}
