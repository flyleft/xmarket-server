package me.jcala.xmarket.server.service;

import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.entity.TradeTag;
import me.jcala.xmarket.server.admin.init.SysColName;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.entity.document.Message;
import me.jcala.xmarket.server.entity.document.Team;
import me.jcala.xmarket.server.entity.document.Trade;
import me.jcala.xmarket.server.entity.dto.Result;
import me.jcala.xmarket.server.exception.SysDataException;
import me.jcala.xmarket.server.repository.*;
import me.jcala.xmarket.server.service.inter.HybridService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.FileTool;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HybridServiceImpl implements HybridService{

    private SystemGetRepository systemCrudRepository;
    private TeamRepository teamRepository;
    private CustomRepository customRepository;
    private MessageRepository messageRepository;
    private TradeRepository tradeRepository;
    private ApplicationInfo info;

    @Autowired
    public HybridServiceImpl(SystemGetRepository systemCrudRepository, TeamRepository teamRepository,
                             CustomRepository customRepository, MessageRepository messageRepository,
                             TradeRepository tradeRepository, ApplicationInfo info) {
        this.systemCrudRepository = systemCrudRepository;
        this.teamRepository = teamRepository;
        this.customRepository = customRepository;
        this.messageRepository = messageRepository;
        this.tradeRepository = tradeRepository;
        this.info = info;
    }

    @Override
    public ResponseEntity<?> getTradeTagList() {
        String col= SysColName.colTradeTag.name();
        SystemBean systemBean= systemCrudRepository.findByName(col);
        if (systemBean==null){
            throw new RuntimeException("商品标签为空,请检查数据库中systemBean集合数据是否完整");
        }
        List<TradeTag> tradeTags=systemBean.getTradeTags();
        Result<List<TradeTag>> result=new Result<List<TradeTag>>().api(Api.SUCCESS);
        result.setData(tradeTags);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getTradeTagNameList() {
        String col= SysColName.colTradeTag.name();
        SystemBean systemBean= systemCrudRepository.findByName(col);
        if (systemBean==null){
            throw new RuntimeException("商品标签为空,请检查数据库中systemBean集合数据是否完整");
        }
        List<String> tagNames=systemBean.getTradeTags().stream()
                .map(TradeTag::getName)
                .collect(Collectors.toList());
        Result<List<String>> result=new Result<List<String>>().api(Api.SUCCESS);
        result.setData(tagNames);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getTeamListBySchoolName(String schoolName) {
        List<Team> teamList=teamRepository.findAllBySchoolName(schoolName);
        Result<List<Team>> result=new Result<List<Team>>().api(Api.SUCCESS);
        result.setData(teamList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createTeam(Team team) {
        if (team==null){
            return RespFactory.INSTANCE().paramsError();
        }
        Team teamData=teamRepository.save(team);
        if (teamData==null){
            throw new RuntimeException("添加Team数据时发生了异常!");
        }
        customRepository.updateUserTeams(team.getSponsor().getId(),teamData.getId());

        return RespFactory.INSTANCE().ok();
    }

    @Override
    public ResponseEntity<?> getSchoolList() {
        String name= SysColName.colSchool.name();
        SystemBean bean= systemCrudRepository.findByName(name);
        if (bean==null||bean.getSchools()==null){
            throw new SysDataException("sys集合数据不完整,请检查或者重新初始化");
        }
        Result<List<String>> result=new Result<List<String>>().api(Api.SUCCESS);
        result.setData(bean.getSchools());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createDeal(String fromId,String fromName,String fromAvatar,String tradeId) {
        if (CustomValidator.hasEmpty(fromId,fromName,fromAvatar,tradeId)){
            return RespFactory.INSTANCE().paramsError();
        }

        Trade trade=tradeRepository.findPartsById(tradeId);

        if (trade==null){
            return RespFactory.INSTANCE().paramsError();
        }
        customRepository.updateUserTrades("toBeConfirmTrades",fromId,tradeId);
        String belongId=trade.getAuthor().getId();
        Message message=new Message();
        message.setTradeId(tradeId);
        message.setTradeImg(trade.getImgUrls().get(0));
        message.setBelongId(belongId);
        message.setUserId(fromId);
        message.setKind(1);
        message.setUsername(fromName);
        message.setUserAvatar(fromAvatar);
        messageRepository.save(message);
        return RespFactory.INSTANCE().ok();
    }

    @Override
    public ResponseEntity<byte[]> gainPic(String dir, String picName) {
        File file=new File(info.getPicHome()+File.separatorChar+dir+File.separatorChar+picName);
        byte[] bytes;
        try {
            bytes= FileTool.readFileToByteArray(file);
        } catch (Exception e) {
            log.warn(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", picName);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
