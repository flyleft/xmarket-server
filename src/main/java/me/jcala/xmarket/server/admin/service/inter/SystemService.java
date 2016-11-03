package me.jcala.xmarket.server.admin.service.inter;

import me.jcala.xmarket.server.admin.entity.Authority;
import me.jcala.xmarket.server.admin.entity.Role;

import java.util.List;

/**
 * 与学校有关的service
 */
public interface SystemService {
    Authority findAuthorityByUsername(String username);
    List<Role> findRolesByAuthorityName(String username);
    List<String> findPermissionsByAuthorityName(String username);
}
