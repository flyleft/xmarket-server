package me.jcala.xmarket.server.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.entity.configuration.TradeType;
import me.jcala.xmarket.server.entity.document.*;
import me.jcala.xmarket.server.entity.pojo.Result;
import me.jcala.xmarket.server.repository.*;
import me.jcala.xmarket.server.service.inter.UserService;
import me.jcala.xmarket.server.utils.CustomValidator;
import me.jcala.xmarket.server.utils.RespFactory;
import me.jcala.xmarket.server.utils.FileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private CustomRepositoryImpl customRepository;

    private MessageRepository messageRepository;

    private TradeRepository tradeRepository;

    private TeamRepository teamRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CustomRepositoryImpl customRepository,
                           MessageRepository messageRepository, TradeRepository tradeRepository,
                           TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.customRepository = customRepository;
        this.messageRepository = messageRepository;
        this.tradeRepository = tradeRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public ResponseEntity<?> login(String username, String password) {
        if (CustomValidator.hasEmpty(username,password)){
            return RespFactory.INSTANCE().paramsError();
        }
        Optional<User> user=userRepository.findByUsernameAndPassword(username,password);
        if (!user.isPresent()){
            if (userRepository.countByUsername(username)<1){
                return new ResponseEntity<>(new Result<String>().api(Api.USER_NOT_EXIST), HttpStatus.OK);
            }
            return new ResponseEntity<>(new Result<String>().api(Api.USER_PASS_ERR),HttpStatus.OK);
        }else {
            User userData=user.get();
            Result<User> result=new Result<>();
            result.api(Api.SUCCESS);
            result.setData(userData);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> auth(String username, String password) {
        if (CustomValidator.hasEmpty(username,password)){
            return RespFactory.INSTANCE().paramsError();
        }
        Optional<User> user=userRepository.findByUsernameAndPassword(username,password);
        if (!user.isPresent()){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_PASS_ERR),HttpStatus.OK);
        }
        String token=createJWT("xmarket","jcala",user.get().getId(),ApplicationInfo.getJwtLife());
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

    /**
     *
     * @param id         密钥 ID
     * @param issuer     jwt签发者
     * @param subject    所要传输的数据
     * @param ttlMillis  token生命周期(单位:毫秒)
     * @return 加密后JWT token字符串
     */
    private String createJWT(String id, String issuer, String subject, long ttlMillis) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(ApplicationInfo.getJwtKey());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    @Override
    public ResponseEntity<?> register(String username, String password){
        if (CustomValidator.hasEmpty(username,password)){
            return RespFactory.INSTANCE().paramsError();
        }else if (userRepository.countByUsername(username)>0){
           return new ResponseEntity<>(new Result<String>().api(Api.USER_NAME_EXIST),HttpStatus.OK);
        }else {
                User user=userRepository.insert(
                        new UserBuilder()
                                .username(username)
                                .password(password)
                                .avatarUrl("img/avatar.jpg")//设置用户默认头像，即/resources/static/img/avatar.jpg
                                .build()
                );

                if (user==null || user.getId()==null){
                  throw new RuntimeException("UserInfoServiceImpl:用户注册向数据库插入数据时发生异常");
                }
            Result<String> result=new Result<>();
            result.api(Api.SUCCESS);
            result.setData(user.getId());
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<?> updatePhoneSchool(String id,String phone, String school){

        if (CustomValidator.hasEmpty(id,school)){
            return RespFactory.INSTANCE().paramsError();
        }
        User user=userRepository.findOne(id);
        if (user==null){
            return RespFactory.INSTANCE().notFoundError();
        }
        if (userRepository.countByPhone(phone) > 0){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_PHONE_EXIST),HttpStatus.OK);
        }
        customRepository.updateUserPhoneSchool(id,phone,school);
        Result<User> userResult=new Result<User>().api(Api.SUCCESS);
        user.setSchool(school);
        user.setPhone(phone);
        userResult.setData(user);
        return new ResponseEntity<>(userResult,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updatePassword(String id, String oldPass, String newPass){
        if (CustomValidator.hasEmpty(id,oldPass,newPass)){
            return RespFactory.INSTANCE().paramsError();
        }
        long num=userRepository.countByIdAndPassword(id,oldPass);
        if (num<1){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_OLD_PASS_ERR),HttpStatus.OK);
        }else if (userRepository.countById(id)<1){
            return RespFactory.INSTANCE().notFoundError();
        }
        customRepository.updateUserPassword(id,newPass);
        return RespFactory.INSTANCE().ok();
    }

    @Override
    public ResponseEntity<?> updateAvatar(String id, HttpServletRequest request) throws Exception{
        if (CustomValidator.hasEmpty(id)){
            return RespFactory.INSTANCE().paramsError();
        }
        if (userRepository.countById(id)<1){
            return RespFactory.INSTANCE().notFoundError();
        }
        String url= FileTool.uploadFile(ApplicationInfo.getPicHome(),request);
        customRepository.updateUserAvatar(id,url);
        return RespFactory.INSTANCE().ok();
    }

    @Override
    public ResponseEntity<?> getTrades(TradeType type, String userId) {
        if (CustomValidator.hasEmpty(userId)){
            return RespFactory.INSTANCE().paramsError();
        }
        User user=userRepository.findOne(userId);
        if (user==null){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_NOT_EXIST), HttpStatus.NOT_FOUND);
        }
        Iterable<Trade> trades=new ArrayList<>();
        switch (type){
            case SELL:
                if (user.getSellTrades()!=null){
                    trades=tradeRepository.findAll(user.getSellTrades());
                }
                break;
            case SOLD:
                if (user.getSoldTrades()!=null){
                    trades=tradeRepository.findAll(user.getSoldTrades());
                }
                break;
            case BOUGHT:
                if (user.getBoughtTrades()!=null){
                    trades=tradeRepository.findAll(user.getBoughtTrades());
                }
                break;
            case DONATE:
                if (user.getDonateTrades()!=null){
                    trades=tradeRepository.findAll(user.getDonateTrades());
                }
                break;
            case TO_BE_CONFIRMED:
                if (user.getToBeConfirmTrades()!=null){
                    trades=tradeRepository.findAll(user.getToBeConfirmTrades());
                }
                break;
            default:break;
        }

        Result<List<Trade>> result=new Result<List<Trade>>().api(Api.SUCCESS);
        List<Trade> tradeList=new ArrayList<>();
        trades.forEach(tradeList::add);
        result.setData(tradeList);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getMessages(String userId, int msgNum, Pageable page) {
        int num=(int) messageRepository.count();
        if (msgNum >= num){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_MSG_LATEST),HttpStatus.OK);
        }
        Page<Message> messagePage= messageRepository.findByBelongId(userId,page);
        Result<List<Message>> result=new Result<List<Message>>().api(Api.SUCCESS);
        result.setData(messagePage.getContent());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> donateTrade(String userId,String tradeId,String tradeImg,String team) {
        if (CustomValidator.hasEmpty(userId,tradeId,tradeImg,team)){
            return RespFactory.INSTANCE().paramsError();
        }
        User user=userRepository.findOne(userId);
        Team teamBean=teamRepository.findByName(team);
        if (user==null||teamBean==null){
            return RespFactory.INSTANCE().notFoundError();
        }

        //1.修改商品status
        customRepository.updateTradeStatus(tradeId,2);
        //2.用户商品去除在售，加入捐赠
        customRepository.deleteFromUserTrades("sellTrades",userId,tradeId);
        customRepository.addToUserTrades("donateTrades",userId,tradeId);
        //3.志愿队的商品列表加入tradeId
        customRepository.addToTeamTrades(team,tradeId);
        //4.生成志愿队发起人的消息
        Message message=new Message();
        message.setBelongId(teamBean.getAuthorId());
        message.setKind(3);
        message.setUserId(user.getId());
        message.setUserAvatar(user.getAvatarUrl());
        message.setUsername(user.getUsername());
        message.setUserPhone(user.getPhone());
        message.setTradeId(tradeId);
        message.setTradeImg(tradeImg);
        messageRepository.save(message);
        return RespFactory.INSTANCE().ok();
    }

    @Override
    public ResponseEntity<?> getTeams(String userId) {
        if (CustomValidator.hasEmpty(userId)){
            return RespFactory.INSTANCE().paramsError();
        }
        User user=userRepository.findOne(userId);
        if (user==null){
            return new ResponseEntity<>(new Result<String>().api(Api.USER_NOT_EXIST), HttpStatus.NOT_FOUND);
        }
        Iterable<Team> teams=teamRepository.findAll(user.getTeams());
        List<Team> teamList=new ArrayList<>();
        teams.forEach(teamList::add);

        Result<List<Team>> result=new Result<List<Team>>().api(Api.SUCCESS);
        result.setData(teamList);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
