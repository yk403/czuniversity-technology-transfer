package com.itts.userservice.service.yh.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.userservice.mapper.yh.TYhJsGlMapper;
import com.itts.userservice.model.yh.TYhJsGl;
import com.itts.userservice.service.yh.TYhJsGlService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
@Service
public class TYhJsGlServiceImpl implements TYhJsGlService {

    @Resource
    private TYhJsGlMapper tYhJsGlMapper;

    //通过用户id查出角色id
    @Override
    public Long selectbyid(Long id) {
        QueryWrapper<String> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yh_id",id)
                    .eq("sfsc","false");
        TYhJsGl tYhJsGl = tYhJsGlMapper.selectById(queryWrapper);
        Long jsId = tYhJsGl.getJsId();
        return jsId;
    }


    //通过用户id查出所拥有的菜单

}
