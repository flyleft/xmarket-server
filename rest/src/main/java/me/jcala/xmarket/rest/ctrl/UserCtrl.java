package me.jcala.xmarket.rest.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.jcala.xmarket.pre.entity.User;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Api("跟用户有关的api")
@Path("/user")
@Produces({ MediaType.APPLICATION_JSON })
@RestController
public class UserCtrl {

   /*
    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
   @ApiImplicitParam(paramType="path",name="id",dataType="int",required=true,value="Pet的ID",defaultValue="1")})
    @ApiResponses({
        @ApiResponse(code=400,message="请求参数没填好"),
        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
     })
    */
    @GET
    @Path("/echo")
    @ApiOperation("测试是否可以正常返回user内容")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对") })
    public User echo() {
        User user=new User();
        user.setUsername("jcala");
        user.setPassword("md5");
        return user;
    }
}
