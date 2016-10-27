package me.jcala.xmarket.server.entity.document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 封装消息的javabean
 */
@ToString
@Setter
@Getter
public class Message {
    private String content;//消息内容
    private User fromUser;//消息发送方
    private User toUser;//消息接收方
    private Deal deal;//消息关注的商品
}
