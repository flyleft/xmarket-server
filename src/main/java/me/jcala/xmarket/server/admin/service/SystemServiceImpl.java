package me.jcala.xmarket.server.admin.service;

import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.entity.dto.ResultBuilder;
import me.jcala.xmarket.server.admin.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemRepository schoolRepository;

    @Override
    public Result<List<String>> gainSchoolNameList() throws RuntimeException{
        //new ResultBuilder<List<String>>().Code(1).data(strings).build();
        return null;
    }

    public void init(){
        initSchool();
    }
    private void initSchool(){

    }
}
