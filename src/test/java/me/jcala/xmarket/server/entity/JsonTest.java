package me.jcala.xmarket.server.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试jackson对带有Builder的javaBean转换的json数据
 */
public class JsonTest {
    private ObjectMapper mapper;
    private TestBean bean;
    private String json;
    @Before
    public void initData(){
        mapper = new ObjectMapper();
        //bean=TestBean.builder().username("小王").password("585").build();
        bean=new TestBean();
        bean.setUsername("user");
        bean.setPassword("pass");
    }
    @Test
    public void test2Json() throws Exception{
        json =  mapper.writeValueAsString(bean);
        System.out.println(json);
    }

    @After
    public void test2Obj() throws Exception{
        TestBean bean1 = mapper.readValue(json, TestBean.class);
        System.out.println(bean1);
    }


}
