package com.itts.userservice.dto;

import lombok.Data;

import java.util.List;

/**
 * 菜单树
 */
@Data
public class MenuDTO {

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单父级ID
     */
    private Long parentId;

    /**
     * 菜单路径
     */
    private String url;

    /**
     * 菜单曾经
     */
    private String level;

    /**
     * 子级菜单列表
     */
    private List<MenuDTO> childMenus;

}

