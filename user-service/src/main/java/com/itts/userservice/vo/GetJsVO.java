package com.itts.userservice.vo;

import com.itts.userservice.model.js.Js;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：获取角色详情对象
 * @Author：lym
 * @Date: 2021/4/15
 */
@Data
@ApiModel("获取角色详情对象")
public class GetJsVO extends Js implements Serializable {

    /**
     * 角色菜单操作关联集合
     */
    @ApiModelProperty("角色菜单操作关联集合")
    private List<GetJsCdGlVO> jsCdGls;
}