package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.service.inter.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("跟学校有关的api")
@RestController
@RequestMapping("/api/v1/trades")
public class TradeController {

    private TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }


    @ApiOperation(value = "获取指定分类下的商品",response = Result.class,produces = "application/json;charset=UTF-8")
    @GetMapping(value = "/tag/{tagId}/get",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> gainTradeListByTag(@PathVariable("tagId") String tagId){

        return tradeService.getTradeListBySort(tagId);
    }


    @ApiOperation(value = "通过id获取商品的详细信息",response = Result.class,produces = "application/json;charset=UTF-8")
    @GetMapping(value = "/{tradeId}/get",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> gainTradeDetailById(@PathVariable("tradeId") String tradeId){

        return null;
    }

    @ApiOperation(value = "获取学校商品列表",response = Result.class,produces = "application/json;charset=UTF-8")
    @GetMapping(value = "/school/{schoolName}/get",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> gainSchoolTradeList(@PathVariable("schoolName") String schoolName){

        return tradeService.getTradeListBySchoolName(schoolName);
    }

}
