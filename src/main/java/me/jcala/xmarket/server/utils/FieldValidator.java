package me.jcala.xmarket.server.utils;

public class FieldValidator {

    public static boolean hasEmpty(String...strings){
        for (String str:strings){
            if (str==null||str.isEmpty()){
                return true;
            }
        }
        return false;
    }
    public static boolean hasNull(Object...objects){
        for (Object object:objects){
            if(object==null){
                return true;
            }
        }
        return false;
    }
}
