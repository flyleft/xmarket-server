package me.jcala.xmarket.server.admin.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "sys")
@Setter
@Getter
@Builder
public class SystemBean {
    @Id
    private String id;
    private String name;//唯一不可重复
    private List<String> schools;
    private Authority authority;
}
class Authority {
    private String id;
    private String username;
    private String password;
    private List<Role> roles;

}
class Role{
    List<String> permissions;
}