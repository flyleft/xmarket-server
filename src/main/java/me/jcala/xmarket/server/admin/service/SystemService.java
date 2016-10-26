package me.jcala.xmarket.server.admin.service;

import me.jcala.xmarket.server.entity.dto.Result;

import java.util.List;

/**
 * 与学校有关的service
 */
public interface SystemService {
    Result<List<String>> gainSchoolNameList() throws RuntimeException;
}
