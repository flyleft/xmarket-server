package me.jcala.xmarket.server.admin.service;

import me.jcala.xmarket.server.admin.entity.TradeTag;
import me.jcala.xmarket.server.admin.repository.SystemCrudRepository;
import me.jcala.xmarket.server.admin.service.inter.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService{

    private SystemCrudRepository systemCrudRepository;

    @Autowired
    public TradeServiceImpl(SystemCrudRepository systemCrudRepository) {
        this.systemCrudRepository = systemCrudRepository;
    }

    @Override
    public void addTradeTag(TradeTag tag) {


    }

}
