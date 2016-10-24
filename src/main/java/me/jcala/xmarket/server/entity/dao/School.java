package me.jcala.xmarket.server.entity.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "school")
@Setter
@Getter
public class School {
    @Id
    private String id;
    private String name;//唯一不可重复
}
