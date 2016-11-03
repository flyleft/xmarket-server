package me.jcala.xmarket.server.admin.shiro;

import me.jcala.xmarket.server.admin.service.SystemServiceImpl;
import me.jcala.xmarket.server.admin.service.inter.SystemService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MongoReam extends AuthorizingRealm {

    private SystemService systemService;

    @Autowired
    public MongoReam(SystemService systemService) {
        super();
        this.systemService = systemService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String usernmae=principals.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken)
            throws AuthenticationException {
        if(!(authToken instanceof UsernamePasswordToken)) {
            throw new AuthenticationException("This realm can only supports UsernamePasswordToken");
        }
        UsernamePasswordToken token=(UsernamePasswordToken) authToken;
        if(token.getUsername() == null) {
            throw new AuthenticationException("Token can not be null");
        }

        //return new SimpleAuthenticationInfo(username, password, getName());
        return null;
}
}
