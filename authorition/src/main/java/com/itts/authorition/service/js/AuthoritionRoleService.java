package com.itts.authorition.service.js;

import com.github.pagehelper.PageInfo;
import com.itts.authorition.model.js.AuthoritionRole;


/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
public interface AuthoritionRoleService {
    /**
     * 获取详情
     */
    AuthoritionRole get(Long id);
}
