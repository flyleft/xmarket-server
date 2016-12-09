package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.service.inter.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 与学校相关的控制器
 */
@Api("跟学校有关的api")
@RestController
public class SchoolController {

    private SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @ApiOperation(value = "获取学校名称列表",response = Result.class,produces = "application/json;charset=UTF-8")
    @GetMapping(value = "/api/v1/schools/names/get",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> gainSchoolList(){
        return schoolService.getSchoolList();
    }

}
