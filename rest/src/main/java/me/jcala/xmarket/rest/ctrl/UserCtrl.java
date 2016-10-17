package me.jcala.xmarket.rest.ctrl;

import me.jcala.xmarket.pre.entity.UserBean;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces({ MediaType.APPLICATION_JSON })
@Component
public class UserCtrl {
    @Path("/echo")
    @GET
    public UserBean echo() {
        UserBean user=new UserBean();
        user.setUsername("jcala");
        user.setPassword("md5");
        return user;
    }
}
