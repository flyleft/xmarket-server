package me.jcala.xmarket.server.admin.service;

import me.jcala.xmarket.server.admin.entity.Authority;
import me.jcala.xmarket.server.admin.entity.Role;
import me.jcala.xmarket.server.admin.service.inter.SystemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SystemServiceImpl implements SystemService {
    @Override
    public Authority findAuthorityByUsername(String username) {
        return null;
    }

    @Override
    public Set<String> findRolesByAuthorityName(String username) {
        return null;
    }

    @Override
    public Set<String> findPermissionsByAuthorityName(String username) {
        return null;
    }
}
