package com.itts.authorition.service.js.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.authorition.mapper.js.AuthoritionRoleMapper;
import com.itts.authorition.model.js.AuthoritionRole;
import com.itts.authorition.service.js.AuthoritionRoleService;
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
public class AuthoritionRoleServiceImpl implements AuthoritionRoleService {


    @Resource
    private AuthoritionRoleMapper tJsMapper;

    /**
     * 获取详情
     */
    @Override
    public AuthoritionRole get(Long id) {
        AuthoritionRole tJs = tJsMapper.selectById(id);
        return tJs;
    }
}
