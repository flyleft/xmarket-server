package me.jcala.xmarket.server.entity.document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 封装消息的javabean
 */
@Document(collection = "message")
@ToString
@Setter
@Getter
public class Message {

    @Id
    private String id;
    @Transient
    private String reqMsgId;
    private String belongId;//消息所属者的id
    private String userId;//交易对方用户的id
    private String username;//交易对方用户的用户名
    private String userAvatar;//交易对方用户的头像地址
    private String userPhone;//交易对方用户的手机号。如果kind为0则手机号可见；如果kind为1则手机号不可见，设置默认值""
    private String tradeId;//商品id
    private String tradeImg;//商品的封面
    private int kind;//kind表示信息类型。0表示买到商品的消息;1表示收到购买请求的消息;2表示已完成

}
