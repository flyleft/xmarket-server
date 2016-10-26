package me.jcala.xmarket.server.entity;

import me.jcala.xmarket.server.admin.profile.SysColName;
import org.junit.Test;

public class EnumTest {
    @Test
  public void testEnum(){
        System.out.println(SysColName.COL_CACHE.name().toLowerCase());
  }
}
