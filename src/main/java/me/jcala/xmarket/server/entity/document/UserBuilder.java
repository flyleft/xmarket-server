package me.jcala.xmarket.server.entity.document;

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

    public UserBuilder phone(final String phone){
        user.setPhone(phone);
        return this;
    }

    public UserBuilder school(final String school){
        user.setSchool(school);
        return this;
    }
    public User build(){
        return this.user;
    }
}
