package me.jcala.xmarket.server.admin.service;

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
       }
    }

    private boolean needInit() {//判断是否需要初始化操作
        return systemRepository.count() < 1;//如果sys的条数为0，则需要进行初始化操作
    }
    private void initSchool() throws RuntimeException{
        String schoolName= SysColName.COL_SCHOOL.name().toLowerCase();
        systemRepository.save(
                SystemBean.builder()
                        .name(schoolName)
                        .schools(new School().getSchoolList())
                        .build()
        );
    }
}
