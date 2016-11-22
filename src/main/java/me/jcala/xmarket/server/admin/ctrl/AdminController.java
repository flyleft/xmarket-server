package me.jcala.xmarket.server.admin.ctrl;

import io.swagger.annotations.Api;
import me.jcala.xmarket.server.admin.entity.TradeTag;
import me.jcala.xmarket.server.admin.service.inter.SystemService;
import me.jcala.xmarket.server.admin.service.inter.TradeService;
import me.jcala.xmarket.server.entity.dto.Result;
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

    private SystemService systemService;

    private TradeService tradeService;


    @Autowired
    public AdminController(SystemService systemService, TradeService tradeService) {
        this.systemService = systemService;
        this.tradeService = tradeService;
    }

    @GetMapping(value = "/tags/add/update")
    ResponseEntity<?> addTag(TradeTag tag){

        return null;
    }


}
