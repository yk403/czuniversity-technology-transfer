package com.itts.userservice.service.js;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.request.AddJsCdRequest;
import com.itts.userservice.request.AddJsRequest;

import java.util.List;


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
    PageInfo<Js> findByPage(Integer pageNum, Integer pageSize, String name, String systemType);

    /**
     * 通过系统类型是否为默认角色获取角色信息
     *
     * @param systemType  系统类型
     * @param defaultFlag 是否为默认角色
     * @return
     * @author liuyingming
     */
    List<Js> findBySystemTypeAndDefault(String systemType, Boolean defaultFlag);

    /**
     * 获取详情
     */
    Js get(Long id);

    /**
     * 新增
     */
    Js add(AddJsRequest request);

    /**
     * 更新
     */
    Js update(Js Js);
}
