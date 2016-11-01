package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.admin.service.inter.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 与学校相关的控制器
 */
@Api("跟学校有关的api")
@RestController
public class SchoolController {

    private SystemService schoolService;

    @Autowired
    public SchoolController(SystemService schoolService) {
        this.schoolService = schoolService;
    }
}
