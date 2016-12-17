package me.jcala.xmarket.server.admin.service;

import me.jcala.xmarket.server.admin.entity.Authority;
import me.jcala.xmarket.server.admin.entity.Role;
import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.init.SchoolInitData;
import me.jcala.xmarket.server.admin.init.SysColName;
import me.jcala.xmarket.server.admin.init.TradeTagsInitData;
import me.jcala.xmarket.server.admin.repository.SystemCrudRepository;
import me.jcala.xmarket.server.admin.service.inter.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;

@Repository
public class InitServiceImpl implements InitService{

    private static final Logger LOGGER = LoggerFactory.getLogger(InitService.class);
    private SystemCrudRepository systemCrudRepository;
    private boolean applicationInfoInit;

    @Autowired
    public InitServiceImpl(SystemCrudRepository systemCrudRepository, boolean applicationInfoInit) {
        this.systemCrudRepository = systemCrudRepository;
        this.applicationInfoInit = applicationInfoInit;
    }

    @PostConstruct
    @Override
    public void init() {
       if (needInit()){
           LOGGER.info("正在进行系统数据初始化...");
           initSchool();
           initAuthority();
           initTradeTags();
           LOGGER.info("系统数据初始化完成...");
       }
    }

    private boolean needInit() {//判断是否需要初始化操作
        return systemCrudRepository.count() < 3;//如果sys的条数小于3，则需要进行初始化操作
    }

    private void initSchool(){
        String colSchool= SysColName.colSchool.name();
        SystemBean system=new SystemBean();
        system.setSchools(new SchoolInitData().getSchoolList());
        system.setName(colSchool);
        systemCrudRepository.save(system);
    }

    private void initAuthority(){
        String colAuthor=SysColName.colAuthority.name();
        SystemBean system=new SystemBean();
        Role role=new Role("admin",new HashSet<>(Arrays.asList("USER:*","ADMIN:*")));
        Authority authority=new Authority("admin","admin","c312",
                new HashSet<>(Arrays.asList(role)));
        system.setAuthorities(new HashSet<>(Arrays.asList(authority)));
        system.setName(colAuthor);
        systemCrudRepository.save(system);
    }

    private void initTradeTags(){
        String colTradeTags=SysColName.colTradeTag.name();
        SystemBean system=new SystemBean();
        system.setName(colTradeTags);
        system.setTradeTags(new TradeTagsInitData().getTradeTags());
        systemCrudRepository.save(system);
    }

}
