package com.itts.userservice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/8
 */
@Data
@ApiModel(value = "数据字典模块")
public class SjzdModelVO implements Serializable {

    private static final long serialVersionUID = -3917946869390230527L;

    /**
     * 所属模块编号
     */
    @ApiModelProperty(value = "所属模块编号")
    private String ssmk;

    /**
     * 所属模块名称
     */
    @ApiModelProperty(value = "所属模块名称")
    private String ssmkmc;

    /**
     * 系统类别
     */
    @ApiModelProperty(value = "系统类别")
    private String xtlb;

    /**
     * 模块类别
     */
    @ApiModelProperty(value = "模块类别")
    private String mklx;
}