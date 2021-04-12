package com.itts.userservice.service.sjzd.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.userservice.model.sjzd.Sjzd;
import com.itts.userservice.mapper.sjzd.SjzdMapper;
import com.itts.userservice.service.sjzd.SjzdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        return null;
    }

    @Override
    public Sjzd get(Long id) {
        Sjzd sjzd = sjzdMapper.selectById(id);
        return sjzd;
    }

    /**
     * 通过名称或编码查询
     * @param string
     * @return
     */
    @Override
    public Sjzd selectByString(String string) {
        Sjzd sjzd = sjzdMapper.selectByCode(string);
        if(sjzd ==null){
            Sjzd sjzd1 = sjzdMapper.selectByName(string);
            if(sjzd1 ==null){
                throw new WebException((ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR));
            }else{
                return sjzd1;
            }
        }
        return sjzd;
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
