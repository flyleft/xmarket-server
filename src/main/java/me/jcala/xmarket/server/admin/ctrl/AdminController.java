package me.jcala.xmarket.server.admin.ctrl;

import io.swagger.annotations.Api;
import me.jcala.xmarket.server.admin.service.SystemService;
import me.jcala.xmarket.server.entity.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员端的控制器
 */
@Api("管理员的api")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private SystemService service;

    @Autowired
    public AdminController(SystemService service) {
        this.service = service;
    }

    @PostMapping(value = "/init")
    public Result<String> initSystem(){
        return null;
    }

}
