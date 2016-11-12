package me.jcala.xmarket.server.service;

import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.profile.SysColName;
import me.jcala.xmarket.server.admin.repository.SystemCrudRepository;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.document.Trade;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.exception.SysDataException;
import me.jcala.xmarket.server.repository.SystemGetRepository;
import me.jcala.xmarket.server.repository.TradeRepository;
import me.jcala.xmarket.server.service.inter.SchoolService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    private SystemGetRepository systemCrudRepository;

    private TradeRepository tradeRepository;

    @Autowired
    public SchoolServiceImpl(SystemGetRepository systemCrudRepository, TradeRepository tradeRepository) {
        this.systemCrudRepository = systemCrudRepository;
        this.tradeRepository = tradeRepository;
    }

    /**
     GET /school_list                          获取学校列表
     获取成功:       自定义状态码100  HttpStatus200 content包含school列表
     获取失败:       自定义状态码101  HttpStatus500
     */
    @Override
    public ResponseEntity<?> getSchoolList(){
        String name= SysColName.colSchool.name().toLowerCase();
        SystemBean bean= systemCrudRepository.findByName(name);
        if (bean==null||bean.getSchools()==null){
            throw new SysDataException("sys集合数据不完整,请检查或者重新初始化");
        }
        Result<List<String>> result=new Result<List<String>>().api(Api.SUCCESS);
        result.setData(bean.getSchools());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getSchoolTradeList(String schoolName) {
        if (CustomValidator.hasEmpty(schoolName)){
            return RespFactory.INSTANCE().paramsError();
        }
        Result<List<Trade>> result=new Result<List<Trade>>().api(Api.SUCCESS);
        List<Trade> trades=tradeRepository.findBySchoolName(schoolName);
        result.setData(trades);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
