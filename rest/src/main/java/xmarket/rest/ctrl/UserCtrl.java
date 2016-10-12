package xmarket.rest.ctrl;

import xmarket.server.entity.transfer.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCtrl {
    @RequestMapping("/proto/read")
     User.Register protoRead() {
        return User.Register.newBuilder().setUsername("小王").setPassword("sdnskdks").setPhone("182003855656546").build();
    }
}
