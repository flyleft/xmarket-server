package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.jcala.xmarket.server.service.inter.StaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StaticController {

    private StaticService staticService;

    @Autowired
    public StaticController(StaticService staticService) {
        this.staticService = staticService;
    }

    @ApiOperation(value = "获取用户头像",response = byte[].class,produces = "application/json;charset=UTF-8")
    @ApiResponses({
            @ApiResponse(code=404,message="没有找到该图片")
    })
    @GetMapping(value = "/pic/{dir}/{pic_name:.+}",produces = "image/jpeg;image/png;image/gif")
    public ResponseEntity<byte[]> gainUserAvatar(@PathVariable("dir")String dir, @PathVariable("pic_name") String picName)
            throws RuntimeException {
        return staticService.gainPic(dir,picName);
    }

}
