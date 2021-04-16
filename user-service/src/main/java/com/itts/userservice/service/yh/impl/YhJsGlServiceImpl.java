package com.itts.userservice.service.yh.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.userservice.mapper.yh.YhJsGlMapper;
import com.itts.userservice.model.yh.YhJsGl;
import com.itts.userservice.service.yh.YhJsGlService;
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
public class YhJsGlServiceImpl implements YhJsGlService {

    @Resource
    private YhJsGlMapper yhJsGlMapper;


}
