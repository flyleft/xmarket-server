package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.entity.dao.DealItem;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.service.inter.SchoolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService{

    @Override
    public Result<List<String>> gainSchoolNameList() {
        return null;
    }

    @Override
    public List<DealItem> gainLatestDealList() {
        return null;
    }
}
