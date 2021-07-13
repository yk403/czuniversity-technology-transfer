package com.itts.userservice.service.cd;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.dto.GetCdAndCzDTO;
import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.model.cz.Cz;
import com.itts.userservice.model.js.Js;
import com.itts.userservice.request.AddCdRequest;
import com.itts.userservice.vo.CdTreeVO;

import java.util.List;

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
    PageInfo<GetCdAndCzDTO> findByPage(Integer pageNum, Integer pageSize, String name, String systemType, String modelType);

    /**
     * 通过ID获取当前菜单及其子菜单（树形）
     */
    List<CdTreeVO> findByTree(List<Cd> cds);

    /**
     * 通过角色获取菜单操作
     */
    List<Cz> getOptionsByRole(List<Js> js, Long menuId);

    /**
     * 通过角色获取菜单
     */
    List<CdTreeVO> getMenuByRole(List<Js> js);

    /**
     * 通过菜单编码获取当前菜单和所有子菜单
     */
    List<Cd> findThisAndAllChildrenByCode(String code);

    /**
     * 通过名称和编码获取列表
     */
    PageInfo<GetCdAndCzDTO> findByNameOrCodePage(Integer pageNum, Integer pageSize, String qurey, String systemType, String modelType);

    /**
     * 通过父级菜单ID获取其子级信息
     */
    List<Cd> findByParentId(Long parentId, String systemType);

    /**
     * 通过父级菜单ID获取其子级数量
     */
    Long countByParentId(Long parentId);

    /**
     * 通过ID获取详情
     */
    Cd getById(Long id);

    /**
     * 通过ID获取菜单、菜单关联操作
     */
    GetCdAndCzDTO getCdAndCzById(Long id);

    /**
     * 新增
     */
    Cd add(AddCdRequest cd);

    /**
     * 更新
     */
    Cd update(AddCdRequest cd, Cd old);

    /**
     * 删除
     */
    void delete(Cd id);

}
