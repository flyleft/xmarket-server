package me.jcala.xmarket.server.admin.service;

import me.jcala.xmarket.server.admin.entity.TradeTag;
import me.jcala.xmarket.server.admin.repository.SystemCrudRepository;
import me.jcala.xmarket.server.admin.service.inter.AdminTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminTradeServiceImpl implements AdminTradeService {

    private SystemCrudRepository systemCrudRepository;

    @Autowired
    public AdminTradeServiceImpl(SystemCrudRepository systemCrudRepository) {
        this.systemCrudRepository = systemCrudRepository;
    }

    @Override
    public ResponseEntity<?> addTradeTag(TradeTag tag) {

       return null;
    }

}
