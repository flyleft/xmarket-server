package me.jcala.xmarket.server.admin.shiro;

import me.jcala.xmarket.server.admin.entity.Authority;
import me.jcala.xmarket.server.admin.service.inter.AuthorityService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;

public class MongoReam extends AuthorizingRealm {

    @Autowired
    private AuthorityService systemService;

    /**
     * 权限验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = principals.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = systemService.findRolesByAuthorityName(username);
        Set<String> permissions = systemService.findPermissionsByAuthorityName(username);
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户账号
        String username = token.getPrincipal().toString();
        Authority authority = systemService.findAuthorityByUsername(username);
        if (authority != null) {
            return  new SimpleAuthenticationInfo(authority.getUsername(),
                    authority.getPassword(),"mongo_realm");
        }
        return null;
    }
}
