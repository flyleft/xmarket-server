package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.entity.dao.DealItem;
import me.jcala.xmarket.server.entity.dao.School;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.repository.SchoolRepository;
import me.jcala.xmarket.server.service.inter.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolServiceImpl implements SchoolService{

    @Autowired
    private SchoolRepository repository;

    @Override
    public Result<List<String>> gainSchoolNameList() {
        Result<List<String>> result=new Result<>();
        List<String> schoolNameList=repository.findAllBy()
                                              .filter(x -> x.getName()!=null&&x.getName().length()>0)
                                              .map(School::getName)
                                              .collect(Collectors.toList());
        result.setCode(1);
        result.setData(schoolNameList);
        return result;
    }

    @Override
    public List<DealItem> gainLatestDealList() {
        return null;
    }
}
