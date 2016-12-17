package me.jcala.xmarket.server.entity.configuration;

/**
 * 封装整个应用全局配置
 */
public class ApplicationInfo {
    private static String picHome;
    private static String jwtKey;
    private static String address;
    private static long jwtLife;

    public static String getPicHome() {
        return picHome;
    }

    public static void setPicHome(String picHome) {
        ApplicationInfo.picHome = picHome;
    }

    public static String getJwtKey() {
        return jwtKey;
    }

    public static void setJwtKey(String jwtKey) {
        ApplicationInfo.jwtKey = jwtKey;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        ApplicationInfo.address = address;
    }

    public static long getJwtLife() {
        return jwtLife;
    }

    public static void setJwtLife(long jwtLife) {
        ApplicationInfo.jwtLife = jwtLife;
    }
}
