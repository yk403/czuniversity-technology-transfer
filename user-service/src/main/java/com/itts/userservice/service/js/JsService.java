package com.itts.userservice.service.js;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.request.AddJsRequest;
import com.itts.userservice.vo.GetJsVO;

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
    PageInfo<Js> findByPage(Integer pageNum, Integer pageSize, String name, String systemType,Long jgId);

    /**
     * 通过系统类型是否为默认角色获取角色信息
     *
     * @param userType    用户类型
     * @param defaultFlag 是否为默认角色
     * @return
     * @author liuyingming
     */
    List<Js> findByUserTypeAndDefault(String userType, Boolean defaultFlag);

    /**
     * 通过用户ID获取
     */
    List<Js> findByUserId(Long userId);

    /**
     * 获取详情
     */
    Js get(Long id);

    /**
     * 通过ID获取角色菜单操作关联信息
     */
    GetJsVO getJsCdCzGl(Long id);

    /**
     * 新增
     */
    Js add(AddJsRequest request);

    /**
     * 更新
     */
    Js update(Js js);

    /**
     * 删除
     */
    void delete(Js js);

    /**
     * 更新 - 更新角色、角色菜单关联、角色菜单操作关联
     */
    Js updateJsCdCzGl(AddJsRequest js);

    /**
     * 通过角色ID和菜单ID删除角色菜单关联
     */
    void deleteJsCdGlByJsIdAndCdIds(Long jsId, List<Long> cdIds);

    /**
     * 通过角色ID和菜单ID和操作ID删除角色菜单操作关联
     */
    void deleteJsCdCzGlByJsIdAndCdIdAndCzIds(Long jsId, Long cdId, List<Long> czIds);
}
