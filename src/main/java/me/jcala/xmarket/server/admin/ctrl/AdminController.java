package me.jcala.xmarket.server.admin.ctrl;

import io.swagger.annotations.Api;
import me.jcala.xmarket.server.admin.entity.TradeTag;
import me.jcala.xmarket.server.admin.service.inter.AuthorityService;
import me.jcala.xmarket.server.admin.service.inter.AdminTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员端的控制器
 */
@Api("管理员的api")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private AuthorityService systemService;

    private AdminTradeService adminTradeService;


    @Autowired
    public AdminController(AuthorityService systemService, AdminTradeService adminTradeService) {
        this.systemService = systemService;
        this.adminTradeService = adminTradeService;
    }

    @GetMapping(value = "/tags/add/update")
    ResponseEntity<?> addTag(TradeTag tag){

        return adminTradeService.addTradeTag(tag);
    }


}
