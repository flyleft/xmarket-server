package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.jcala.xmarket.server.entity.document.Trade;
import me.jcala.xmarket.server.entity.dto.Result;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("跟用户交易有关的api")
@RestController
@RequestMapping("/api/v1/users/")
public class UserTradeController {

    @ApiOperation(value = "发布商品",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/{user_id}/trade/create",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> create(@PathVariable("user_id") String userId, Trade trade){
        return null;
    }

    @ApiOperation(value = "获取捐赠商品列表",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/{user_id}/donate",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> donates(@PathVariable("user_id") String userId){
        return null;
    }

    @ApiOperation(value = "获取卖出商品列表",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/{user_id}/sold",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> sold(@PathVariable("user_id") String userId){
        return null;
    }

    @ApiOperation(value = "获取买到商品列表",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/{user_id}/bought",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> bought(@PathVariable("user_id") String userId){
        return null;
    }

    @ApiOperation(value = "获取待售商品列表",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/{user_id}/for_sale",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> forSale(@PathVariable("user_id") String userId){
        return null;
    }

    @ApiOperation(value = "获取待确认商品列表",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/{user_id}/to_be_confirm",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> toBeConfirm(@PathVariable("user_id") String userId){
        return null;
    }
}
