package me.jcala.xmarket.rest.ctrl;

import io.swagger.annotations.*;
import me.jcala.xmarket.pre.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("跟用户有关的api")
@RestController
@RequestMapping("/user")
public class TestController {

    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path",name="id",dataType="int",required=true,value="Pet的ID",defaultValue="1")})
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    public User getUserInfoById() {
        User user=new User();
        user.setUsername("jcala");
        user.setPassword("md5");
        return user;
    }
}
