package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.*;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.service.inter.StaticService;
import me.jcala.xmarket.server.service.inter.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Api("跟用户信息有关的api")
@RestController
@RequestMapping("/api/v1")
public class UserInfoController {

    private UserInfoService userInfoService;

    private StaticService staticService;


    @Autowired
    public UserInfoController(UserInfoService userInfoService, StaticService staticService) {
        this.userInfoService = userInfoService;
        this.staticService = staticService;
    }

    @ApiOperation(value = "用户登录并获取token",response = Result.class,produces = "application/json;charset=UTF-8")
    @ApiResponses({
            @ApiResponse(code=200,message="登录成功,用户名错误,密码错误"),
            @ApiResponse(code=500,message="服务器异常"),
            @ApiResponse(code=400,message="请求参数不合法")
    })
    @PostMapping(value = "/auth",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> authenticate(String username,String password){
        return userInfoService.loginAndGetToken(username,password);
    }

    @ApiOperation(value = "用户注册",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/users/register",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> register(String username,String password,String phone){
     return userInfoService.register(username,password,phone);
   }

    @ApiOperation(value = "设置用户学校信息",response = Result.class,produces = "application/json;charset=UTF-8")
    @PutMapping(value = "/users/{user_id}/school/update",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<?> updateUserSchool(@PathVariable("user_id") String id,String school){
       return userInfoService.updateSchool(id,school);
   }

    @ApiOperation(value = "修改用户密码",response = Result.class,produces = "application/json;charset=UTF-8")
    @PutMapping(value = "/{user_id}/pass/update", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateUserPassword(@PathVariable("user_id")String id,String oldPass,String newPass)
            throws RuntimeException{
        return userInfoService.updatePassword(id,oldPass,newPass);
    }
    @ApiOperation(value = "修改用户头像",response = Result.class,produces = "application/json;charset=UTF-8")
    @PutMapping(value = "/users/{user_id}/avatar/update",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateUserAvatar(@PathVariable("user_id")String id, HttpServletRequest request)
            throws Exception{
      return userInfoService.updateAvatar(id,request);
    }

    @ApiOperation(value = "获取用户志愿队信息",response = Result.class,produces = "application/json;charset=UTF-8")
    @GetMapping(value = "/users/{user_id}/teams/get",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> gainUserTeam(@PathVariable("user_id")String id) throws RuntimeException{
        return null;
    }

    @ApiOperation(value = "获取用户头像",response = byte[].class,produces = "application/json;charset=UTF-8")
    @ApiResponses({
            @ApiResponse(code=404,message="没有找到该图片")
    })
    @GetMapping(value = "/pic/{dir}/{pic_name:.+}",produces = "image/jpeg;image/png;image/gif")
    public ResponseEntity<byte[]> gainUserAvatar(@PathVariable("dir")String dir,@PathVariable("pic_name") String picName)
            throws RuntimeException {
        return staticService.gainPic(dir,picName);
    }


}
