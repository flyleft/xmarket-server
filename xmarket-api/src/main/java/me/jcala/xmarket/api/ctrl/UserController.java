package me.jcala.xmarket.api.ctrl;

import io.swagger.annotations.*;
import me.jcala.xmarket.ser.dto.Result;
import me.jcala.xmarket.mongo.dao.User;
import me.jcala.xmarket.ser.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("跟用户有关的api")
@RestController
@RequestMapping("/rest/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("测试user的echo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path",name="id",dataType="int",required=true,value="Pet的ID",defaultValue="1")})
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "echo",produces =  "application/json;charset=UTF-8")
    public User echo() {
        User user=new User();
        user.setUsername("jcala");
        user.setPassword("md5");
        return user;
    }

    @GetMapping(value = "echo1",produces =  "application/json;charset=UTF-8")
    public Result<String> login(){
        userService.login("");
        return new Result<>();
    }

}
