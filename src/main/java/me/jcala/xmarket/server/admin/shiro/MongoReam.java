package me.jcala.xmarket.server.admin.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MongoReam extends AuthorizingRealm {

    public MongoReam() {
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

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
