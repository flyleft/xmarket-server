package me.jcala.xmarket.server.admin.service;

import me.jcala.xmarket.server.admin.entity.Authority;
import me.jcala.xmarket.server.admin.service.inter.AuthorityService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthorityServiceImpl implements AuthorityService {
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
