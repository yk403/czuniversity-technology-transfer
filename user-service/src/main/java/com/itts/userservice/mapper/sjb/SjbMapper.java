package com.itts.userservice.mapper.sjb;

import com.itts.userservice.model.sjb.Bjg;
import com.itts.userservice.model.sjb.Bzd;
import com.itts.userservice.model.sjb.Sjb;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/16
 */
@Repository
public interface SjbMapper {

    /**
     * 获取数据表列表
     */
    List<Sjb> findTables(@Param("name") String name);

    /**
     * 获取表结构详情
     */
    List<Bzd> getTableDetails(@Param("tableName") String tableName);
}