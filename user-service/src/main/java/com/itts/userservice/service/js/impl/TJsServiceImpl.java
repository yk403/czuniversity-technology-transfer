package com.itts.userservice.service.js.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.userservice.mapper.js.TJsMapper;
import com.itts.userservice.model.js.TJs;
import com.itts.userservice.service.js.TJsService;
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
public class TJsServiceImpl  implements TJsService {


    @Resource
    private TJsMapper tJsMapper;
    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<TJs> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<TJs> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sfsc",false);
        List<TJs> tJs = tJsMapper.selectList(objectQueryWrapper);
        PageInfo<TJs> tJsPageInfo = new PageInfo<>(tJs);
        return tJsPageInfo;
    }

    /**
     * 获取详情
     */
    @Override
    public TJs get(Long id) {
        TJs tJs = tJsMapper.selectById(id);
        return tJs;
    }

    /**
     * 新增
     */
    @Override
    public TJs add(TJs tJs) {
        tJsMapper.insert(tJs);
        return tJs;
    }

    /**
     * 更新
     */
    @Override
    public TJs update(TJs tJs) {
        tJsMapper.updateById(tJs);
        return tJs;
    }
}
