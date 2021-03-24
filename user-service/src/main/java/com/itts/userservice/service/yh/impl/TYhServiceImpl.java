package com.itts.userservice.service.yh.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.yh.TYh;
import com.itts.userservice.mapper.yh.TYhMapper;
import com.itts.userservice.service.yh.TYhService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
@Service
@Primary
public class TYhServiceImpl implements TYhService {

    @Resource
    private TYhMapper tYhMapper;


    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<TYh> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<TYh> query = new QueryWrapper<>();
        query.eq("sfsc", false);
        List<TYh> list = tYhMapper.selectList(query);
        PageInfo<TYh> page = new PageInfo<>(list);
        return page;
    }

    /**
     * 获取详情
     */
    @Override
    public TYh get(Long id) {
        TYh tYh = tYhMapper.selectById(id);
        return tYh;
    }

    /**
     * 新增
     */
    @Override
    public TYh add(TYh tYh) {
        tYhMapper.insert(tYh);
        return tYh;
    }

    /**
     * 更新
     */
    @Override
    public TYh update(TYh tYh) {
        tYhMapper.updateById(tYh);
        return tYh;
    }
}
