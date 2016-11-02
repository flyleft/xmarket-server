package me.jcala.xmarket.server.entity.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Authority {
    @Id
    private String id;
    private String username;
    private String password;
    private List<Role> roles;

}
class Role{
   List<String> permissions;
}
