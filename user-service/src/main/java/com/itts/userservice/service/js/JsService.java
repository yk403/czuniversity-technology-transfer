package com.itts.userservice.service.js;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.js.Js;


/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
public interface JsService {

    /**
     * 查询列表
     */
    PageInfo<Js> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 获取详情
     */
    Js get(Long id);
    /**
     * 新增
     */
    Js add(Js Js);
    /**
     * 更新
     */
    Js update(Js Js);
}
