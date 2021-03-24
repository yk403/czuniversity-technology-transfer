package com.itts.userservice.service.yh;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.yh.Yh;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
public interface YhService {

    /**
     * 查询列表
     */
    PageInfo<Yh> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 获取详情
     */
    Yh get(Long id);

    /**
     * 新增
     */
    Yh add(Yh Yh);

    /**
     * 更新
     */
    Yh update(Yh Yh);
}
