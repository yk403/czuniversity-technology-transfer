package com.itts.userservice.service.yh;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.yh.TYh;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
public interface TYhService {

    /**
     * 查询列表
     */
    PageInfo<TYh> findByPage(Integer pageNum,Integer pageSize);

    /**
     * 获取详情
     */
    TYh get(Long id);

    /**
     * 新增
     */
    TYh add(TYh tYh);

    /**
     * 更新
     */
    TYh update(TYh tYh);
}
