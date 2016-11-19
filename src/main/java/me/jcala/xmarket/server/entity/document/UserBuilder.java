package me.jcala.xmarket.server.entity.document;

/**
 * 为了方便给User链式赋值创建的辅助类
 */
public class  UserBuilder {

    private final User user=new User();


    public UserBuilder username(final String username){
        user.setUsername(username);
        return this;
    }

    public UserBuilder password(final String password){
        user.setPassword(password);
        return this;
    }

    public UserBuilder avatarUrl(final String avatarUrl){
        user.setAvatarUrl(avatarUrl);
        return this;
    }

    public User build(){
        return this.user;
    }
}
