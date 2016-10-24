package me.jcala.xmarket.server.service.inter;

import me.jcala.xmarket.server.entity.dao.DealItem;
import me.jcala.xmarket.server.entity.dto.Result;

import java.util.List;

/**
 * 与学校有关的service
 */
public interface SchoolService {
    Result<List<String>> gainSchoolNameList();
    List<DealItem> gainLatestDealList();
}
