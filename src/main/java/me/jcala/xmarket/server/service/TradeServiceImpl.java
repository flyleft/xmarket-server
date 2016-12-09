package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.document.Trade;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.repository.TradeRepository;
import me.jcala.xmarket.server.service.inter.TradeService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {


    private TradeRepository tradeRepository;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public ResponseEntity<?> getTradeListBySort(String sortId) {
        if (CustomValidator.hasEmpty(sortId)){
            return RespFactory.INSTANCE().paramsError();
        }

        Result<List<Trade>> result=new Result<List<Trade>>().api(Api.SUCCESS);
        List<Trade> trades=tradeRepository.findByTagId(sortId);
        result.setData(trades);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getTradeDetailById(String tradeId) {
        if (CustomValidator.hasEmpty(tradeId)){
            return RespFactory.INSTANCE().paramsError();
        }
       Trade trade= tradeRepository.findById(tradeId);
       if (trade==null){
           return RespFactory.INSTANCE().paramsError();
       }
        Result<Trade> result=new Result<Trade>().api(Api.SUCCESS);
        result.setData(trade);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getTradeListBySchoolName(String schoolName) {
        if (CustomValidator.hasEmpty(schoolName)){
            return RespFactory.INSTANCE().paramsError();
        }
        Result<List<Trade>> result=new Result<List<Trade>>().api(Api.SUCCESS);
        List<Trade> trades=tradeRepository.findBySchoolName(schoolName);
        result.setData(trades);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
