package me.jcala.xmarket.rest.ctrl;

import me.jcala.xmarket.pre.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class TestController {

    @RequestMapping(value = "/test/user", method = RequestMethod.GET)
    public User getUserInfoById(int id) {
        User user=new User();
        user.setUsername("jcala");
        user.setPassword("md5");
        return user;
    }
}
