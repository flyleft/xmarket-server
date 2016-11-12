package me.jcala.xmarket.server.admin.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(collection = "sys")
@Setter
@Getter
public class SystemBean {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;//唯一不可重复
    private List<String> schools;//学校列表
    private Set<Authority> authorities;//shiro权限列
    private List<TradeTag> tradeTags;//系统商品分类表
}