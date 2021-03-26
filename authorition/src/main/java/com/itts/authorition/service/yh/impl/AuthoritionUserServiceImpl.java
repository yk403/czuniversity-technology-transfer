package com.itts.authorition.service.yh.impl;

import com.itts.authorition.mapper.yh.AuthoritionUserMapper;
import com.itts.authorition.model.yh.AuthoritionUser;
import com.itts.authorition.service.yh.AuthoritionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
@Service
@Primary
public class AuthoritionUserServiceImpl implements AuthoritionUserService {

    @Autowired
    private AuthoritionUserMapper tYhMapper;

    /**
     * 通过账号获取用户信息
     */
    @Override
    public AuthoritionUser getByUserName(String userName) {

        AuthoritionUser yh = tYhMapper.getByUserName(userName);
        return yh;
    }

    /**
     * 获取详情
     */
    @Override
    public AuthoritionUser get(Long id) {
        AuthoritionUser tYh = tYhMapper.selectById(id);
        return tYh;
    }
}
