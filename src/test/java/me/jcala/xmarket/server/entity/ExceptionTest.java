package me.jcala.xmarket.server.entity;

import me.jcala.xmarket.server.exception.SysDataException;
import org.junit.Test;

public class ExceptionTest {

    @Test
    public void testException(){
        System.out.println("1");
        int i=1;
        if (i==1){
            new SysDataException().error();
            System.out.println("2");
        }
        System.out.println("3");
    }

}
