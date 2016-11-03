package me.jcala.xmarket.server.entity.configuration;

import lombok.Builder;
import lombok.Getter;

/**
 * 封装整个应用全局配置
 */
@Builder
@Getter
public class ApplicationInfo {
    private String picHome;
    private String jwtKey;
    private long jwtLife;
}
