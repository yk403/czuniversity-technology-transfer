package com.itts.userservice.service.js.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.itts.userservice.mapper.js.JsCdGlMapper;
import com.itts.userservice.model.js.JsCdGl;
import com.itts.userservice.service.js.JsCdGlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
@Service
public class JsCdGlServiceImpl implements JsCdGlService {

    @Resource
    private JsCdGlMapper jsCdGlMapper;


}
