package com.itts.userservice.service.sjzd.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.userservice.model.sjzd.Sjzd;
import com.itts.userservice.mapper.sjzd.SjzdMapper;
import com.itts.userservice.service.sjzd.SjzdService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-31
 */
@Service
public class SjzdServiceImpl implements SjzdService {

    @Resource
    private SjzdMapper sjzdMapper;
    /**
     * 获取列表
     */
    @Override
    public PageInfo<Sjzd> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Sjzd> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sfsc",false);
        List<Sjzd> sjzds = sjzdMapper.selectList(objectQueryWrapper);
        PageInfo<Sjzd> shzdPageInfo = new PageInfo<>(sjzds);
        return shzdPageInfo;
    }

    /**
     * 获取指定模块列表
     */
    @Override
    public PageInfo<Sjzd> findAppointByPage(Integer pageNum, Integer pageSize, String ssmk) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Sjzd> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sfsc",false)
                            .eq("ssmk",ssmk);
        List<Sjzd> sjzds = sjzdMapper.selectList(objectQueryWrapper);
        PageInfo<Sjzd> shzdPageInfo = new PageInfo<>(sjzds);
        return shzdPageInfo;
    }

    @Override
    public Sjzd get(Long id) {
        Sjzd sjzd = sjzdMapper.selectById(id);
        return sjzd;
    }

    @Override
    public PageInfo<Sjzd> selectByString(Integer pageNum, Integer pageSize, String string, String ssmk) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Sjzd> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sfsc",false)
                .eq("ssmk",ssmk)
        .eq("zdmc",string);
        List<Sjzd> sjzds = sjzdMapper.selectList(objectQueryWrapper);
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Sjzd> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("sfsc",false)
                .eq("ssmk",ssmk)
                .eq("zdbm",string);
        List<Sjzd> sjzdList = sjzdMapper.selectList(objectQueryWrapper);
        sjzds.remove(sjzdList);
        sjzds.addAll(sjzdList);
        PageInfo<Sjzd> shzdPageInfo = new PageInfo<>(sjzds);
        return shzdPageInfo;
    }



    @Override
    public Sjzd add(Sjzd sjzd) {
        sjzdMapper.insert(sjzd);
        return sjzd;
    }

    @Override
    public Sjzd update(Sjzd sjzd) {
        sjzdMapper.updateById(sjzd);
        return sjzd;
    }
}
