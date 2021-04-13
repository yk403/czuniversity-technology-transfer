package com.itts.userservice.service.cd.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.userservice.mapper.cd.CdMapper;
import com.itts.userservice.mapper.js.JsMapper;
import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.service.cd.CdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
@Service
public class CdServiceImpl implements CdService {

    @Resource
    private CdMapper cdMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Cd> findByPage(Integer pageNum, Integer pageSize, String name, String systemType, String modelType) {

        PageHelper.startPage(pageNum,pageSize);

        QueryWrapper<Cd> query = new QueryWrapper<>();

        query.eq("sfsc",false);
        if(StringUtils.isNotBlank(name)){
            query.like("cdmc", name);
        }
        if(StringUtils.isNotBlank(systemType)){
            query.eq("xtlx", systemType);
        }
        if(StringUtils.isNotBlank(modelType)){
            query.eq("mklx", modelType);
        }

        List<Cd> cd = cdMapper.selectList(query);
        PageInfo<Cd> tJsPageInfo = new PageInfo<>(cd);

        return tJsPageInfo;
    }

    /**
     * 获取详情
     */
    @Override
    public Cd get(Long id) {

        Cd cd = cdMapper.selectById(id);
        return cd;
    }

    /**
     * 新增
     */
    @Override
    public Cd add(Cd cd) {
        cdMapper.insert(cd);
        return cd;
    }

    /**
     * 更新
     */
    @Override
    public Cd update(Cd cd) {
        cdMapper.updateById(cd);
        return cd;
    }
}
