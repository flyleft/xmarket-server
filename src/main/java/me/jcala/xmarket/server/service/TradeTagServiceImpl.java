package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.profile.SysColName;
import me.jcala.xmarket.server.admin.repository.SystemRepository;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.document.Trade;
import me.jcala.xmarket.server.entity.document.TradeTag;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.repository.TradeRepository;
import me.jcala.xmarket.server.service.inter.TradeTagService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeTagServiceImpl implements TradeTagService {

    private SystemRepository systemRepository;

    private TradeRepository tradeRepository;

    @Autowired
    public TradeTagServiceImpl(SystemRepository systemRepository, TradeRepository tradeRepository) {
        this.systemRepository = systemRepository;
        this.tradeRepository = tradeRepository;
    }

    @Override
    public ResponseEntity<?> getTradeSortList() {
         String col= SysColName.colTradeTag.name();
         SystemBean systemBean=systemRepository.findByName(col);
         if (systemBean==null){
             throw new RuntimeException("商品标签为空,请检查数据库中systemBean集合数据是否完整");
         }
         List<TradeTag> tradeTags=systemBean.getTradeTags();
        Result<List<TradeTag>> result=new Result<List<TradeTag>>().api(Api.SUCCESS);
        result.setData(tradeTags);
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
}
