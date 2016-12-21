package me.jcala.xmarket.server.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.jcala.xmarket.server.conf.ApiConf;
import me.jcala.xmarket.server.entity.document.Message;
import me.jcala.xmarket.server.entity.pojo.Result;
import me.jcala.xmarket.server.service.inter.HybridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 与学校相关的控制器
 */
@Api("跟学校有关的api")
@RestController
public class HybridController {

    private HybridService hybridService;

    @Autowired
    public HybridController(HybridService hybridService) {
        this.hybridService = hybridService;
    }

    @ApiOperation(value = "创建一个新的志愿队",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = ApiConf.create_team,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createTeam(@RequestParam String team,HttpServletRequest request){
        return hybridService.createTeam(team,request);
    }

    @ApiOperation(value = "获取本校所有志愿队列表",response = Result.class,produces = "application/json;charset=UTF-8")
    @GetMapping(value = ApiConf.get_school_teams,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> gainTeamList(@PathVariable("schoolName") String schoolName, int type, Pageable page){
        return hybridService.getTeamListBySchoolName(schoolName,type,page);
    }


    @ApiOperation(value = "获取学校名称列表",response = Result.class,produces = "application/json;charset=UTF-8")
    @GetMapping(value = ApiConf.get_schools,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> gainSchoolList(){
        return hybridService.getSchoolList();
    }

    @ApiOperation(value = "获取图片资源",response = byte[].class,produces = "application/json;charset=UTF-8")
    @ApiResponses({
            @ApiResponse(code=404,message="没有找到该图片")
    })
    @GetMapping(value = ApiConf.get_img,produces = "image/jpeg;image/png;image/gif")
    public ResponseEntity<byte[]> gainImg(@PathVariable("dir")String dir, @PathVariable("picName") String picName) {
        return hybridService.gainPic(dir,picName);
    }

    @ApiOperation(value = "获取所有的商品分类",response = Result.class,produces = "application/json;charset=UTF-8")
    @GetMapping(value = ApiConf.get_tags,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> gainTradeTagList(@RequestParam("type") int type){

        if (type==1){
            return hybridService.getTradeTagList();//kind为1返回TradeTag列表
        }else{
            return hybridService.getTradeTagNameList();//kind为2返回String列表
        }

    }

    @ApiOperation(value = "创建交易",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = ApiConf.create_deal,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createDeal(@RequestParam String fromId,@RequestParam String fromName,
                                        @RequestParam String fromAvatar,@RequestParam String tradeId){
        return hybridService.createDeal(fromId,fromName,fromAvatar,tradeId);
    }

    @ApiOperation(value = "确认交易",response = Result.class,produces = "application/json;charset=UTF-8")
    @PostMapping(value = ApiConf.confirm_deal,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> confirmDeal(@RequestParam Message message){
      return hybridService.confirmDeal(message);
    }

}
