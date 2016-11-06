package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.configuration.TradeType;
import me.jcala.xmarket.server.entity.document.Trade;
import me.jcala.xmarket.server.entity.document.User;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.repository.CustomRepository;
import me.jcala.xmarket.server.repository.TradeRepository;
import me.jcala.xmarket.server.repository.UserRepository;
import me.jcala.xmarket.server.service.inter.UserTradeService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class UserTradeServiceImpl implements UserTradeService {

    private TradeRepository tradeRepository;

    private CustomRepository customRepository;

    private UserRepository userRepository;

    @Autowired
    public UserTradeServiceImpl(TradeRepository tradeRepository,
                                CustomRepository customRepository, UserRepository userRepository) {
        this.tradeRepository = tradeRepository;
        this.customRepository = customRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> createTrade(String userId, Trade trade) {
        if (CustomValidator.hasEmpty(userId)||trade==null){
            return RespFactory.INSTANCE().illegal_params();
        }
        Trade tradeData= tradeRepository.save(trade);
        if (tradeData!=null){
            customRepository.updateUserTrades("sell_trades",userId,tradeData.getId());
            return RespFactory.INSTANCE().created();
        }else {
            throw new RuntimeException("some error happened in UserTradeService:交易信息存储失败!");
        }
    }

    @Override
    public ResponseEntity<?> getTrades(TradeType type, String userId) {
        if (CustomValidator.hasEmpty(userId)){
            return RespFactory.INSTANCE().illegal_params();
        }
        User user=userRepository.findOne(userId);
        if (user==null){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_NOT_EXIST), HttpStatus.NOT_FOUND);
        }
        Iterable<Trade> trades=new ArrayList<>();
        switch (type){
            case SELL:
                if (user.getSell_trades()!=null){
                    trades=tradeRepository.findAll(user.getSell_trades());
                }
                break;
            case SOLD:
                if (user.getSold_trades()!=null){
                    trades=tradeRepository.findAll(user.getSold_trades());
                }
                break;
            case BOUGHT:
                if (user.getBought_trades()!=null){
                    trades=tradeRepository.findAll(user.getBought_trades());
                }
                break;
            case DONATE:
                if (user.getDonate_trades()!=null){
                    trades=tradeRepository.findAll(user.getDonate_trades());
                }
                break;
            case TOBECONFIRM:
                if (user.getTo_be_confirm_trades()!=null){
                    trades=tradeRepository.findAll(user.getTo_be_confirm_trades());
                }
                break;
            default:break;
        }
        Result<Iterable<Trade>> result=new Result<Iterable<Trade>>().api(Api.SUCCESS);
        result.setData(trades);

        return new ResponseEntity<Result<Iterable<Trade>>>(HttpStatus.OK);
    }
}
