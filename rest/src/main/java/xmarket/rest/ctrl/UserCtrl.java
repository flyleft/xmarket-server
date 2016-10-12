package xmarket.rest.ctrl;

import xmarket.server.entity.transfer.UserProtos;
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
    public ResponseEntity<UserProtos.User> protoRead() {
        return ResponseEntity.ok(UserProtos.User.newBuilder().setId(1).setName("zhangsan").build());
    }
    @RequestMapping("/proto/write")
    public ResponseEntity<UserProtos.User> protoRead(RequestEntity<UserProtos.User> requestEntity) {
        return ResponseEntity.ok(requestEntity.getBody());
    }
}
