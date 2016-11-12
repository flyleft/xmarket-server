package me.jcala.xmarket.server.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jcala.xmarket.server.entity.configuration.Api;
import me.jcala.xmarket.server.entity.dto.Result;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试jackson对带有Builder的javaBean转换的json数据
 */
public class JsonTest {
    private ObjectMapper mapper;
    private String json;
    private Result<String> result;
    @Before
    public void initData(){
        mapper = new ObjectMapper();
        result=new Result<>();
        result.api(Api.TOKEN_EXPIRED);

    }
    @Test
    public void test2Json() throws Exception{
        json =  mapper.writeValueAsString(result);
        System.out.println(json);
    }

}
