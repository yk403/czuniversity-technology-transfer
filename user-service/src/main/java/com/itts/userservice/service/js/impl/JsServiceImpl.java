package com.itts.userservice.service.js.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.userservice.mapper.js.JsMapper;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.service.js.JsService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
@Service
@Primary
public class JsServiceImpl implements JsService {


    @Resource
    private JsMapper tJsMapper;
    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<Js> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Js> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sfsc",false);
        List<Js> js = tJsMapper.selectList(objectQueryWrapper);
        PageInfo<Js> tJsPageInfo = new PageInfo<>(js);
        return tJsPageInfo;
    }

    /**
     * 获取详情
     */
    @Override
    public Js get(Long id) {
        Js Js = tJsMapper.selectById(id);
        return Js;
    }

    /**
     * 新增
     */
    @Override
    public Js add(Js Js) {
        tJsMapper.insert(Js);
        return Js;
    }

    /**
     * 更新
     */
    @Override
    public Js update(Js Js) {
        tJsMapper.updateById(Js);
        return Js;
    }
}
