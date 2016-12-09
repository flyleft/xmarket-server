package me.jcala.xmarket.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
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
import me.jcala.xmarket.server.utils.FileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserTradeServiceImpl implements UserTradeService {

    private TradeRepository tradeRepository;

    private CustomRepository customRepository;

    private UserRepository userRepository;

    private ApplicationInfo info;

    @Autowired
    public UserTradeServiceImpl(TradeRepository tradeRepository, CustomRepository customRepository,
                                UserRepository userRepository, ApplicationInfo info) {
        this.tradeRepository = tradeRepository;
        this.customRepository = customRepository;
        this.userRepository = userRepository;
        this.info = info;
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
