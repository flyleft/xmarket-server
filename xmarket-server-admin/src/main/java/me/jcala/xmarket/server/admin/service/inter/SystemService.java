package me.jcala.xmarket.server.admin.service.inter;

import me.jcala.xmarket.server.admin.entity.Authority;
import java.util.Set;

/**
 * 与学校有关的service
 */
public interface SystemService {
    Authority findAuthorityByUsername(String username);
    Set<String> findRolesByAuthorityName(String username);
    Set<String> findPermissionsByAuthorityName(String username);
}
