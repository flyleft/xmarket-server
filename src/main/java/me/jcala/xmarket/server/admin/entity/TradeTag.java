package me.jcala.xmarket.server.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 商品分类
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TradeTag {
    private int id;
    private String name;//分类的名称
    private String bgPic;//分类的背景图片
}
