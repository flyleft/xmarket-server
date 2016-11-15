package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.jcala.xmarket.server.service.inter.StaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Api("跟静态资源有关的api")
@Controller
@RequestMapping("/api/v1/static")
public class StaticController {

    private StaticService staticService;

    @Autowired
    public StaticController(StaticService staticService) {
        this.staticService = staticService;
    }

    @ApiOperation(value = "获取图片资源",response = byte[].class,produces = "application/json;charset=UTF-8")
    @ApiResponses({
            @ApiResponse(code=404,message="没有找到该图片")
    })
    @GetMapping(value = "/img/{dir}/{picName:.+}",produces = "image/jpeg;image/png;image/gif")
    public ResponseEntity<byte[]> gainUserAvatar(@PathVariable("dir")String dir, @PathVariable("picName") String picName)
            throws RuntimeException {
        return staticService.gainPic(dir,picName);
    }

    @GetMapping(value = "/img/defaultAvatar",produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView gainDefaultAvatar(){
       return new ModelAndView("/static/defaultAvatar.html");
    }

}
