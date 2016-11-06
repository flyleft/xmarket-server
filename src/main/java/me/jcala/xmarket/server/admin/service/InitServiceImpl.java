package me.jcala.xmarket.server.admin.service;

import me.jcala.xmarket.server.admin.entity.Authority;
import me.jcala.xmarket.server.admin.entity.Role;
import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.profile.School;
import me.jcala.xmarket.server.admin.profile.SysColName;
import me.jcala.xmarket.server.admin.repository.SystemRepository;
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
    private SystemRepository systemRepository;

    @Autowired
    public InitServiceImpl(SystemRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    @PostConstruct
    @Override
    public void init() {
       if (needInit()){
           LOGGER.info("正在进行系统初始化...");
           initSchool();
           initAuthority();
       }
    }

    private boolean needInit() {//判断是否需要初始化操作
        return systemRepository.count() < 2;//如果sys的条数为0，则需要进行初始化操作
    }
    private void initSchool(){
        String colSchool= SysColName.colSchool.name();
        SystemBean system=new SystemBean();
        system.setSchools(new School().getSchoolList());
        system.setName(colSchool);
        systemRepository.save(system);
    }
    private void initAuthority(){
        String colAuthor=SysColName.colAuthority.name();
        SystemBean system=new SystemBean();
        Role role=new Role("admin",new HashSet<>(Arrays.asList("USER:*","ADMIN:*")));
        Authority authority=new Authority("admin","admin","c312",
                new HashSet<>(Arrays.asList(role)));
        system.setAuthorities(new HashSet<>(Arrays.asList(authority)));
        system.setName(colAuthor);
        systemRepository.save(system);
    }
}
