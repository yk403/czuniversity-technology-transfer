package com.itts.userservice.TJs.service;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.TJs.model.TJs;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
public interface TJsService  {

    /**
     * 查询列表
     */
    PageInfo<TJs> findByPage(Integer pageNum,Integer pageSize);

    /**
     * 获取详情
     */
    TJs get(Long id);
    /**
     * 新增
     */
    TJs add(TJs tJs);
    /**
     * 更新
     */
    TJs update(TJs tJs);
}
