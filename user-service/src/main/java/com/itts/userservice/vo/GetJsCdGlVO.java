package com.itts.userservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/15
 */
@Data
@ApiModel("获取角色菜单关联对象")
public class GetJsCdGlVO implements Serializable {

    private static final long serialVersionUID = -6005803194819200818L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String cdmc;

    /**
     * 菜单编码
     */
    @ApiModelProperty("菜单编码")
    private String cdbm;

    /**
     * 角色菜单操作关联集合
     */
    @ApiModelProperty("角色菜单操作关联集合")
    private List<GetJsCdCzGlVO> jsCdCzGls;
}