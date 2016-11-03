package me.jcala.xmarket.server.admin.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "sys")
@Setter
@Getter
public class SystemBean {
    @Id
    private String id;
    private String name;//唯一不可重复
    private List<String> schools;
    private Authority authority;
}