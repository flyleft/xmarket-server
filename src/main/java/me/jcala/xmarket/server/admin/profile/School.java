package me.jcala.xmarket.server.admin.profile;

import java.util.ArrayList;
import java.util.List;

public class School {

    private final List<String> schoolList=new ArrayList<>();

    private void addSchool(){
        schoolList.add("西南交通大学(犀浦校区)");
        schoolList.add("西南交通大学(九里校区)");
        schoolList.add("西南交通大学(峨眉校区)");
        schoolList.add("电子科技大学(沙河校区)");
        schoolList.add("电子科技大学(清水河校区)");
    }

    public List<String> getSchoolList(){
        addSchool();
        return this.schoolList;
    }
}
