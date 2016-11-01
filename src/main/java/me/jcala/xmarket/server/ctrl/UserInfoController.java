package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.exception.ReqParasException;
import me.jcala.xmarket.server.exception.TestException;
import me.jcala.xmarket.server.service.inter.StaticService;
import me.jcala.xmarket.server.service.inter.UserService;
import me.jcala.xmarket.server.utils.FieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api("跟用户信息有关的api")
@RestController
@RequestMapping("/users")
@Slf4j
public class UserInfoController {

    private UserService userService;

    private StaticService staticService;


    @Autowired
    public UserInfoController(UserService userService, StaticService staticService) {
        this.userService = userService;
        this.staticService = staticService;
    }

    @ApiOperation("用户登录")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/login",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result<String> login(String username,String password) throws RuntimeException{
        return userService.login(username,password);
    }

   @ApiOperation("用户注册")
   @PostMapping(value = "/register",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> register(String username,String password,String phone){
       Result<String> result=new Result<>();
       try {
           FieldValidator.hasEmpty(username,password,phone);
           userService.register(username,password,phone);
       } catch (ReqParasException e) {
           log.info("用户注册时错误:"+e.getLocalizedMessage());
           result.setCode(1002);
           result.setMsg("请求参数错误");
           return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
       }
       //return userService.register(username,password,phone);
     return new ResponseEntity<String>(HttpStatus.MULTI_STATUS);
   }
    @ApiOperation("设置用户学校信息")
    @PutMapping(value = "/school",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
   public Result<String> updateUserSchool(String username,String school){
       return userService.updateSchool(username,school);
   }
    @ApiOperation("获取学校名称列表")
    @GetMapping(value = "/school_list",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result<List<String>> getSchoolNameList() throws RuntimeException{
        return userService.gainSchoolList();
    }

    @ApiOperation("修改用户密码")
    @PutMapping(value = "/pass", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result<String> updateUserPassword(String username,String oldPass,String newPass)
            throws RuntimeException{
        return userService.updatePassword(username,oldPass,newPass);
    }
    @ApiOperation("修改用户头像")
    @PutMapping(value = "/avatar",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result<String> updateUserAvatar(String username, HttpServletRequest request)
            throws RuntimeException{
      return userService.updateAvatar(username,request);
    }
    @ApiOperation("获取用户头像")
    @ApiResponses({
            @ApiResponse(code=404,message="没有找到该图片")
    })
    @GetMapping(value = "/pic/{dir}/{picName:.+}")
    public ResponseEntity<byte[]> gainUserAvatar(@PathVariable String dir,@PathVariable String picName)
            throws RuntimeException {
        return staticService.gainPic(dir,picName);
    }
}
