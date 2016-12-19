package me.jcala.xmarket.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.admin.entity.SystemBean;
import me.jcala.xmarket.server.admin.entity.TradeTag;
import me.jcala.xmarket.server.admin.init.SysColName;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.entity.document.Message;
import me.jcala.xmarket.server.entity.document.Team;
import me.jcala.xmarket.server.entity.document.Trade;
import me.jcala.xmarket.server.entity.pojo.Result;
import me.jcala.xmarket.server.exception.SysDataException;
import me.jcala.xmarket.server.repository.*;
import me.jcala.xmarket.server.service.inter.HybridService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.FileTool;
import me.jcala.xmarket.server.utils.RespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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

    @Autowired
    public HybridServiceImpl(SystemGetRepository systemCrudRepository, TeamRepository teamRepository,
                             CustomRepository customRepository, MessageRepository messageRepository,
                             TradeRepository tradeRepository) {
        this.systemCrudRepository = systemCrudRepository;
        this.teamRepository = teamRepository;
        this.customRepository = customRepository;
        this.messageRepository = messageRepository;
        this.tradeRepository = tradeRepository;
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
    public ResponseEntity<?> getTeamListBySchoolName(String schoolName,int type,Pageable page) {
        if (CustomValidator.hasEmpty(schoolName)){
            return RespFactory.INSTANCE().paramsError();
        }
        List<Team> teamList=teamRepository.findAllBySchoolAndStatus(schoolName,true,page);
        if (type==0){
            Result<List<Team>> result=new Result<List<Team>>().api(Api.SUCCESS);
            result.setData(teamList);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        List<String> teamNameList=  teamList.stream()
                                            .map(Team::getName)
                                            .collect(Collectors.toList());

        Result<List<String>> result=new Result<List<String>>().api(Api.SUCCESS);
        result.setData(teamNameList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createTeam(String teamStr, HttpServletRequest request) {
        if (CustomValidator.hasEmpty(teamStr)){
            return RespFactory.INSTANCE().paramsError();
        }
        Team team=null;
        try {
            ObjectMapper mapper=new ObjectMapper();
            Team teamBean=mapper.readValue(teamStr,Team.class);
            List<String> imgUrls= FileTool.uploadMultiFiles(ApplicationInfo.getPicHome(),request);
            teamBean.setStatus(false);
            teamBean.setImg(imgUrls.get(0));
            teamBean.setIdImg(imgUrls.get(1));
            team=teamRepository.save(teamBean);
        } catch (IOException e) {
            log.info("发起志愿队时序列化或者图片存储出错:"+e.getMessage());
        }
        if (team==null){
            return RespFactory.INSTANCE().serverError();
        }
        customRepository.updateUserTeams(team.getAuthorId(),team.getId());

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
        customRepository.addToUserTrades("toBeConfirmTrades",fromId,tradeId);
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
        File file=new File(ApplicationInfo.getPicHome()+File.separatorChar+dir+File.separatorChar+picName);
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

    @Override
    public ResponseEntity<?> confirmDeal(Message message) {

        if (!CustomValidator.checkConfirmMessage(message)){
            return RespFactory.INSTANCE().paramsError();
        }

        String reqMsgId=message.getReqMsgId();
        Message reqMsg=messageRepository.findOne(reqMsgId);
        if (reqMsg==null){
            return RespFactory.INSTANCE().paramsError();
        }
        reqMsg.setKind(2);
        String tradeId=message.getTradeId();
        customRepository.deleteFromUserTrades("sellTrades",message.getUserId(),tradeId);
        customRepository.addToUserTrades("soldTrades",message.getUserId(),tradeId);
        customRepository.deleteFromUserTrades("toBeConfirmTrades",message.getBelongId(),tradeId);
        customRepository.addToUserTrades("boughtTrades",message.getBelongId(),tradeId);
        customRepository.updateTradeStatus(tradeId,1);
        messageRepository.save(reqMsg);
        messageRepository.save(message);
        return RespFactory.INSTANCE().ok();
    }
}
