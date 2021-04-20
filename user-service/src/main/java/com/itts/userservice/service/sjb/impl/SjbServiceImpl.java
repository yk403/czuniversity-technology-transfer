package com.itts.userservice.service.sjb.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.userservice.mapper.sjb.SjbMapper;
import com.itts.userservice.model.sjb.Bjg;
import com.itts.userservice.model.sjb.Bzd;
import com.itts.userservice.model.sjb.Sjb;
import com.itts.userservice.service.sjb.SjbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/16
 */
@Service
public class SjbServiceImpl implements SjbService {

    @Autowired
    private SjbMapper sjbMapper;

    /**
     * 获取数据表列表
     */
    @Override
    public PageInfo<Sjb> findTables(Integer pageNum, Integer pageSize, String name) {

        PageHelper.startPage(pageNum, pageSize);
        List<Sjb> tables = sjbMapper.findTables(name);
        PageInfo<Sjb> pageInfo = new PageInfo<>(tables);
        return pageInfo;
    }

    /**
     * 获取表结构详情
     */
    @Override
    public Bjg getTableDetails(String tableName) {

        Bjg bjg = new Bjg();
        List<Bzd> bzds = sjbMapper.getTableDetails(tableName);

        bjg.setBzds(bzds);

        return bjg;
    }
}