package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.configuration.TradeType;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.service.inter.UserTradeService;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api("跟用户交易有关的api")
@RestController
@RequestMapping("/api/v1/users/")
public class UserTradeController {

    private UserTradeService userTradeService;

    @Autowired
    public UserTradeController(UserTradeService userTradeService) {
        this.userTradeService = userTradeService;
    }

    @ApiOperation(value = "发布商品",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/{userId}/trades/create",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> create(@PathVariable("userId") String userId, String trade, HttpServletRequest request){

        return userTradeService.createTrade(userId,trade,request);
    }

    @ApiOperation(value = "获取商品列表;根据kind的值获取不同类型列表",response = Result.class,produces = "application/json;charset=UTF-8")
    @GetMapping(value = "/{userId}/trades/get",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getTrades(@PathVariable("userId") String userId,int kind){
        if (kind==0){
            return RespFactory.INSTANCE().paramsError();
        }
        switch (kind){
            case 1:return userTradeService.getTrades(TradeType.DONATE,userId);
            case 2:return userTradeService.getTrades(TradeType.SOLD,userId);
            case 3:return userTradeService.getTrades(TradeType.BOUGHT,userId);
            case 4:return userTradeService.getTrades(TradeType.SELL,userId);
            case 5:return userTradeService.getTrades(TradeType.TOBECONFIRM, userId);
            default:return RespFactory.INSTANCE().paramsError();
        }
    }
}
