package com.itts.userservice.dto;

import com.itts.userservice.model.js.Js;
import lombok.Data;

import java.util.List;

/**
 * 角色菜单
 * @author FULI
 */
@Data
public class JsDTO {

    private Long id;
    private String name;
    private String code;

    //角色菜单树
    private List<MenuDTO> menuDTOList;

}
