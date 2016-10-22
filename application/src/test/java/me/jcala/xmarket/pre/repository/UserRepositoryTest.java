package me.jcala.xmarket.pre.repository;

import me.jcala.xmarket.app.Application;
import me.jcala.xmarket.pre.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void testUser() {
           List<User> userList=userRepository.findAll();
           for (User user:userList){
               System.out.println(user);
           }

    }
}
