package me.jcala.xmarket.server.entity.document.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "school")
@Setter
@Getter
public class System {
    @Id
    private String name;//唯一不可重复
    private List<String> schools;
}
