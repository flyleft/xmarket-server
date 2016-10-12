package xmarket.rest.ctrl;

import xmarket.server.entity.transfer.User;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCtrl {
    @GetMapping("/test")
    public String test(){
        System.out.println("测试成功");
        return "测试成功";
    }
    @RequestMapping("/proto/read")
    public ResponseEntity<User.Register> protoRead() {
        return ResponseEntity.ok(User.Register.newBuilder().setUsername("小王").setPassword("sdnskdks").setPhone("182003855656546").build());
    }
    @RequestMapping("/proto/write")
    public ResponseEntity<User.Register> protoRead(RequestEntity<User.Register> requestEntity) {
        return ResponseEntity.ok(requestEntity.getBody());
    }
}
