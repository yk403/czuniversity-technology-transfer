package com.itts.userservice.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色菜单
 *
 * @author FULI
 */
@Data
public class JsDTO {

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编号
     */
    private String code;

    //角色菜单树
    private List<MenuDTO> menuDTOList;

}
