package me.jcala.xmarket.server.dao;

import java.util.List;

public class DealItem {
    private String id;//商品ID
    private String name;//商品名字
    private User author;//商品所属者
    private List<User> wait_trades;//商品待交易者名单
    private User trade;//商品交易者
    private String create_time;//商品创建时间
    private String donate_time;//商品捐赠时间
    private int status;//商品状态。0:在售，1:售出,2:捐赠
}
