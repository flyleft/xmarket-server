package me.jcala.xmarket.server.admin.service;

import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.profile.School;
import me.jcala.xmarket.server.admin.profile.SysColName;
import me.jcala.xmarket.server.admin.repository.SystemRepository;
import me.jcala.xmarket.server.admin.service.inter.SystemService;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.profile.RestIni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemServiceImpl.class);

    private SystemRepository schoolRepository;

    @Autowired
    public SystemServiceImpl(SystemRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Autowired
    public Result<String> init() throws RuntimeException{
        Result<String> result=new Result<>();
        try {
            initSchool();
            result.setCode(RestIni.success);
        } catch (RuntimeException e) {
            result.setCode(RestIni.serverErr);
            result.setMsg("some error happened when init...");
            LOGGER.error(e.getLocalizedMessage());
        }
        return result;
    }

    private void initSchool() throws RuntimeException{
        String schoolName= SysColName.COL_SCHOOL.name().toLowerCase();
        schoolRepository.save(
                SystemBean.builder()
                          .name(schoolName)
                          .schools(new School().getSchoolList())
                          .build()
        );
    }
}
