package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.*;
import me.jcala.xmarket.server.entity.document.User;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("跟用户信息有关的api")
@RestController
@RequestMapping("/api/user")
public class UserInfoController {

    @Autowired
    private UserService userService;


    @ApiOperation("用户登录")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/login",produces="application/json;charset=UTF-8")
    public Result<String> login(String username,String password) throws RuntimeException{
        return userService.login(username,password);
    }

   @ApiOperation("用户注册")
   @PostMapping(value = "/register",produces="application/json;charset=UTF-8")
    public Result<String> register(String username,String password,String phone) throws RuntimeException{
       return userService.register(username,password,phone);
   }
    @ApiOperation("设置用户学校信息")
    @PutMapping(value = "/school",produces="application/json;charset=UTF-8")
   public Result<String> updateUserSchool(String username,String school){
       return userService.updateUserSchool(username,school);
   }
    @ApiOperation("获取学校名称列表")
    @GetMapping(value = "/school_list",produces = "application/json;charset=UTF-8")
    public Result<List<String>> getSchoolNameList() throws RuntimeException{
        return userService.gainSchoolList();
    }

    @ApiOperation("修改用户密码")
    @PutMapping(value = "/pass",produces = "application/json;charset=UTF-8")
    public Result<String> updateUserPassword(String username,String oldPass,String newPass) throws RuntimeException{
        return userService.modifyPassword(username,oldPass,newPass);
    }
}
