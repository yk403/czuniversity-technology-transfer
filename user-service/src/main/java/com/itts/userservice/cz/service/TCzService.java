package com.itts.userservice.cz.service;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.cz.model.TCz;

/**
 * <p>
 * 操作表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
public interface TCzService  {
    /**
     * 查询列表
     */
    PageInfo<TCz> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 获取详情
     */
    TCz get(Long id);

    /**
     * 新增
     */
    TCz add(TCz tCz);

    /**
     * 更新
     */
    TCz update(TCz tCz);

}
