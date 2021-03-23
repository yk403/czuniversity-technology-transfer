package com.itts.userservice.TJs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.userservice.TJs.mapper.TJsMapper;
import com.itts.userservice.TJs.model.TJs;
import com.itts.userservice.TJs.service.TJsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
