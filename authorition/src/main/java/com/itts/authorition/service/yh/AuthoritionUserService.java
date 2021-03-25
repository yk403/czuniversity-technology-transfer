package com.itts.authorition.service.yh;

import com.itts.authorition.model.yh.AuthoritionUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
public interface AuthoritionUserService {
    /**
     * 通过账号获取用户信息
     */
    AuthoritionUser getByUserName(String userName);

    /**
     * 获取详情
     */
    AuthoritionUser get(Long id);
}
