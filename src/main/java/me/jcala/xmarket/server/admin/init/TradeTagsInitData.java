package me.jcala.xmarket.server.admin.init;

import me.jcala.xmarket.server.admin.entity.TradeTag;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易分类的初始化数据
 */

public class TradeTagsInitData {
    private final List<TradeTag> tradeTags=new ArrayList<>();

    public TradeTagsInitData(){
       addTradTag();
    }

    private void addTradTag(){
        tradeTags.add(new TradeTag(1,"学习资料","img/sort_book.jpg"));
        tradeTags.add(new TradeTag(2,"生活用品","img/sort_life.jpg"));
        tradeTags.add(new TradeTag(3,"衣物鞋帽","img/sort_clothes.jpeg"));
        tradeTags.add(new TradeTag(4,"运动健身","img/sort_body.jpg"));
        tradeTags.add(new TradeTag(5,"手机数码","img/sort_phone.jpg"));
        tradeTags.add(new TradeTag(6,"电脑办公","img/sort_computer.jpg"));
        tradeTags.add(new TradeTag(7,"电器","img/sort_elec.jpg"));
        tradeTags.add(new TradeTag(8,"数码配件","img/sort_parts.jpg"));
        tradeTags.add(new TradeTag(9,"租赁","img/sort_rent.jpg"));
        tradeTags.add(new TradeTag(9,"其他","img/sort_other.jpg"));
    }

    public List<TradeTag> getTradeTags() {
        return tradeTags;
    }
}
