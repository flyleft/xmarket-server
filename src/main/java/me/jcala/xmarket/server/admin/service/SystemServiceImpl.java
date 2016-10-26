package me.jcala.xmarket.server.service.admin;

import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.entity.dto.ResultBuilder;
import me.jcala.xmarket.server.repository.admin.SystemRepository;
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
        List<String> strings=schoolRepository.findAllBy()
                                             .map(school -> school.getName())
                                             .collect(Collectors.toList());
        return new ResultBuilder<List<String>>().Code(1).data(strings).build();
    }

}
