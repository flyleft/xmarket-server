package me.jcala.xmarket.server.service.inter;

import me.jcala.xmarket.server.entity.dao.DealItem;
import me.jcala.xmarket.server.entity.dao.School;

import java.util.List;

/**
 * 与学校有关的service
 */
public interface SchoolService {
    List<School> gainSchoolList();
    List<DealItem> gainLatestDealList();
}
