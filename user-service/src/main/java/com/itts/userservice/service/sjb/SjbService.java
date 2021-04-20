package com.itts.userservice.service.sjb;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.sjb.Bjg;
import com.itts.userservice.model.sjb.Bzd;
import com.itts.userservice.model.sjb.Sjb;

/**
 * @Description：数据表Service
 * @Author：lym
 * @Date: 2021/4/16
 */
public interface SjbService {

    /**
     * 获取数据表列表
     */
    PageInfo<Sjb> findTables(Integer pageNum, Integer pageSize, String name);

    /**
     * 获取表结构详情
     */
    Bjg getTableDetails(String tableName);

}
