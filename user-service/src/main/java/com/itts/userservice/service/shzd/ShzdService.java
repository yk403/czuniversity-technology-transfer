package com.itts.userservice.service.shzd;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.shzd.Shzd;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-31
 */
public interface ShzdService {

    /**
     * 获取列表 - 分页
     */
    PageInfo<Shzd> findByPage(Integer pageNum, Integer pageSize);
    /**
     * 获取通过id
     */
    Shzd get(Long id);
    /**
     * 查询，通过名称或编码
     */
    Shzd selectByString(String string);
    /**
     * 新增
     */
    Shzd add(Shzd shzd);
    /**
     * 更新
     */
    Shzd update(Shzd shzd);
}
