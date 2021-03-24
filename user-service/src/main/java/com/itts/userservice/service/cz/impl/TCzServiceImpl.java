package com.itts.userservice.service.cz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.cz.TCz;
import com.itts.userservice.service.cz.TCzService;
import com.itts.userservice.mapper.cz.TCzMapper;
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
public class TCzServiceImpl  implements TCzService {


    @Resource
    private TCzMapper tCzMapper;

    /**
     * 获取列表 - 分页
     */
    @Override
    public PageInfo<TCz> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<TCz> objectQueryWrapper = new QueryWrapper<>();
        //过滤
        objectQueryWrapper.eq("sfsc",false);
        List<TCz> tCzs = tCzMapper.selectList(objectQueryWrapper);
        PageInfo<TCz> tCzPageInfo = new PageInfo<>(tCzs);
        return tCzPageInfo;
    }

    /**
     * 获取详情
     */
    @Override
    public TCz get(Long id) {
        return tCzMapper.selectById(id);
    }

    /**
     * 新增
     */
    @Override
    public TCz add(TCz tCz) {
        tCzMapper.insert(tCz);
        return tCz;
    }

    /**
     * 更新
     */
    @Override
    public TCz update(TCz tCz) {
        tCzMapper.updateById(tCz);
        return tCz;
    }
}
