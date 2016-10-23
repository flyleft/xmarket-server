package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.*;
import me.jcala.xmarket.server.dto.Result;
import me.jcala.xmarket.server.dao.User;
import me.jcala.xmarket.server.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("跟用户有关的api")
@RestController
@RequestMapping("/api/user")
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


    @PostMapping(value = "/{username}",produces="application/json;charset=UTF-8",consumes = "application/json")
    public Result<String> login(@PathVariable("username") String username){

        return new Result<>();
    }

}
