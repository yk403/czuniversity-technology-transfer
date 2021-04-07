package com.itts.userservice.service.shzd.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.model.shzd.Shzd;
import com.itts.userservice.mapper.shzd.ShzdMapper;
import com.itts.userservice.service.shzd.ShzdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ShzdServiceImpl implements ShzdService {

    @Resource
    private ShzdMapper shzdMapper;
    @Override
    public PageInfo<Shzd> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Shzd> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sfsc",false);
        List<Shzd> shzds = shzdMapper.selectList(objectQueryWrapper);
        PageInfo<Shzd> shzdPageInfo = new PageInfo<>(shzds);
        return shzdPageInfo;
    }

    @Override
    public Shzd get(Long id) {
        Shzd shzd = shzdMapper.selectById(id);
        return shzd;
    }

    /**
     * 通过名称或编码查询
     * @param string
     * @return
     */
    @Override
    public Shzd selectByString(String string) {
        Shzd shzd = shzdMapper.selectByCode(string);
        if(shzd==null){
            Shzd shzd1 = shzdMapper.selectByName(string);
            if(shzd1==null){
                throw new WebException((ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR));
            }else{
                return shzd1;
            }
        }
        return shzd;
    }

    @Override
    public Shzd add(Shzd shzd) {
        shzdMapper.insert(shzd);
        return shzd;
    }

    @Override
    public Shzd update(Shzd shzd) {
        shzdMapper.updateById(shzd);
        return shzd;
    }
}
