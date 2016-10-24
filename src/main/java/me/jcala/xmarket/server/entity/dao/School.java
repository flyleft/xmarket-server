package me.jcala.xmarket.server.entity.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "school")
public class School {
    @Id
    private String id;
    private String name;//唯一不可重复
}
