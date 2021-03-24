package com.itts.userservice.service.cz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.cz.Cz;
import com.itts.userservice.service.cz.CzService;
import com.itts.userservice.mapper.cz.CzMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 操作表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
@Service
@Primary
public class CzServiceImpl implements CzService {


    @Resource
    private CzMapper czMapper;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Cz> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Cz> objectQueryWrapper = new QueryWrapper<>();
        //过滤
        objectQueryWrapper.eq("sfsc",false);
        List<Cz> Czs = czMapper.selectList(objectQueryWrapper);
        PageInfo<Cz> tCzPageInfo = new PageInfo<>(Czs);
        return tCzPageInfo;
    }

    /**
     * 获取详情
     */
    @Override
    public Cz get(Long id) {
        return czMapper.selectById(id);
    }

    /**
     * 新增
     */
    @Override
    public Cz add(Cz Cz) {
        czMapper.insert(Cz);
        return Cz;
    }

    /**
     * 更新
     */
    @Override
    public Cz update(Cz Cz) {
        czMapper.updateById(Cz);
        return Cz;
    }
}
