package com.itts.userservice.service.cd;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.cd.Cd;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author lym
 * @since 2021-03-24
 */
public interface CdService {

    /**
     * 查询列表
     */
    PageInfo<Cd> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 获取详情
     */
    Cd get(Long id);

    /**
     * 新增
     */
    Cd add(Cd cd);

    /**
     * 更新
     */
    Cd update(Cd cd);

}
