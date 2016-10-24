package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.*;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("跟用户有关的api")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户登录")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/login",produces="application/json;charset=UTF-8")
    public Result<String> login(String username,String password) throws Exception{
        return userService.login(username,password);
    }

   @ApiOperation("用户注册")
   @PostMapping(value = "/register",produces="application/json;charset=UTF-8")
    public Result<String> register(String username,String password,String phone) throws Exception{
       return userService.register(username,password,phone);
   }
}
