package me.jcala.xmarket.server.utils;

import me.jcala.xmarket.server.exception.ReqParasException;

public class FieldValidator {

    public static void hasEmpty(String...strings){
        for (String str:strings){
            if (str==null||str.isEmpty()){
                throw new ReqParasException("请求参数错误,含有空值");
            }
        }
    }
    public static void hasNull(Object...objects){
        for (Object object:objects){
            if(object==null){
                throw new ReqParasException("请求参数有误，含有空值");
            }
        }
    }
}
