package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.entity.document.Trade;
import me.jcala.xmarket.server.repository.CustomRepository;
import me.jcala.xmarket.server.repository.TradeRepository;
import me.jcala.xmarket.server.service.inter.UserTradeService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class UserTradeServiceImpl implements UserTradeService {

    private TradeRepository tradeRepository;

    private CustomRepository customRepository;

    @Autowired
    public UserTradeServiceImpl(TradeRepository tradeRepository, CustomRepository customRepository) {
        this.tradeRepository = tradeRepository;
        this.customRepository = customRepository;
    }

    @Override
    public ResponseEntity<?> createTrade(String userId, Trade trade) {
        if (CustomValidator.hasEmpty(userId)||trade==null){
            return RespFactory.INSTANCE().illegal_params();
        }
        Trade tradeData= tradeRepository.save(trade);
        if (tradeData!=null){
            String id=tradeData.getId();

        }else {
            throw new RuntimeException("trade信息存储失败!");
        }

        return null;
    }

}
