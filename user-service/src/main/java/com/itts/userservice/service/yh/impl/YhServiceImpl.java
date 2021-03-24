package com.itts.userservice.service.yh.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.mapper.yh.YhMapper;
import com.itts.userservice.service.yh.YhService;
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
public class YhServiceImpl implements YhService {

    @Resource
    private YhMapper tYhMapper;


    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Yh> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Yh> query = new QueryWrapper<>();
        query.eq("sfsc", false);
        List<Yh> list = tYhMapper.selectList(query);
        PageInfo<Yh> page = new PageInfo<>(list);
        return page;
    }

    /**
     * 获取详情
     */
    @Override
    public Yh get(Long id) {
        Yh Yh = tYhMapper.selectById(id);
        return Yh;
    }

    /**
     * 新增
     */
    @Override
    public Yh add(Yh Yh) {
        tYhMapper.insert(Yh);
        return Yh;
    }

    /**
     * 更新
     */
    @Override
    public Yh update(Yh Yh) {
        tYhMapper.updateById(Yh);
        return Yh;
    }
}
