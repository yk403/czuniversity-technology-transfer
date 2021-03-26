package com.itts.userservice.dto;

import lombok.Data;

import java.util.List;

/**
 * 菜单树
 */
@Data
public class MenuDTO {
    // 菜单id
    private Long id;
    // 菜单名称
    private String name;
    // 父菜单id
    private Long parentId;
    // 菜单url
    private String url;
    // 菜单层级
    private String level;
    // 子菜单
    private List<MenuDTO> childMenus;

}

