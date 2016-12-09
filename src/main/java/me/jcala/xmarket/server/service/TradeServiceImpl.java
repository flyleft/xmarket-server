package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.init.SysColName;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.document.Trade;
import me.jcala.xmarket.server.admin.entity.TradeTag;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.repository.SystemGetRepository;
import me.jcala.xmarket.server.repository.TradeRepository;
import me.jcala.xmarket.server.service.inter.TradeService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {

    private SystemGetRepository systemCrudRepository;

    private TradeRepository tradeRepository;

    @Autowired
    public TradeServiceImpl(SystemGetRepository systemCrudRepository, TradeRepository tradeRepository) {
        this.systemCrudRepository = systemCrudRepository;
        this.tradeRepository = tradeRepository;
    }

    @Override
    public ResponseEntity<?> getTradeTagList() {
         String col= SysColName.colTradeTag.name();
         SystemBean systemBean= systemCrudRepository.findByName(col);
         if (systemBean==null){
             throw new RuntimeException("商品标签为空,请检查数据库中systemBean集合数据是否完整");
         }
         List<TradeTag> tradeTags=systemBean.getTradeTags();
        Result<List<TradeTag>> result=new Result<List<TradeTag>>().api(Api.SUCCESS);
        result.setData(tradeTags);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getTradeTagNameList() {
        String col= SysColName.colTradeTag.name();
        SystemBean systemBean= systemCrudRepository.findByName(col);
        if (systemBean==null){
            throw new RuntimeException("商品标签为空,请检查数据库中systemBean集合数据是否完整");
        }
        List<String> tagNames=systemBean.getTradeTags().stream()
                                                        .map(TradeTag::getName)
                                                        .collect(Collectors.toList());
        Result<List<String>> result=new Result<List<String>>().api(Api.SUCCESS);
        result.setData(tagNames);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
