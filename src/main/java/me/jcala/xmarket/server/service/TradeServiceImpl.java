package me.jcala.xmarket.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.entity.document.Trade;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.repository.CustomRepository;
import me.jcala.xmarket.server.repository.TradeRepository;
import me.jcala.xmarket.server.service.inter.TradeService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.FileTool;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService {


    private TradeRepository tradeRepository;
    private CustomRepository customRepository;
    private ApplicationInfo info;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository,
                            CustomRepository customRepository, ApplicationInfo info) {
        this.tradeRepository = tradeRepository;
        this.customRepository = customRepository;
        this.info = info;
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
    public ResponseEntity<?> getTradeListBySchoolName(String schoolName, Pageable page) {
        if (CustomValidator.hasEmpty(schoolName)){
            return RespFactory.INSTANCE().paramsError();
        }

        Result<List<Trade>> result=new Result<List<Trade>>().api(Api.SUCCESS);
        List<Trade> trades=tradeRepository.findBySchoolName(schoolName);
        result.setData(trades);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createTrade(String userId, String trade, HttpServletRequest request) {
        if (CustomValidator.hasEmpty(userId,trade)){
            return RespFactory.INSTANCE().paramsError();
        }
        Trade tradeData=null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            Trade tradeBean = mapper.readValue(trade, Trade.class);
            List<String> imgUrls= FileTool.uploadMultiFiles(info.getPicHome(),request);
            if (imgUrls.size() < 1){
                return RespFactory.INSTANCE().paramsError();
            }
            tradeBean.setImgUrls(imgUrls);
            tradeData = tradeRepository.save(tradeBean);
        } catch (IOException e) {
            log.info("发布商品序列化或者图片存储出错:"+e.getMessage());
        }
        if (tradeData!=null){
            customRepository.updateUserTrades("sell_trades",userId,tradeData.getId());
            return RespFactory.INSTANCE().ok();
        }
        return RespFactory.INSTANCE().paramsError();
    }
}
