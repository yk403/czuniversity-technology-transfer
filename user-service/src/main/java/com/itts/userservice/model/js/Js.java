package com.itts.userservice.model.js;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_js")
@ApiModel("角色对象")
public class Js implements Serializable {

    private static final long serialVersionUID = -409494400980209645L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", required = true)
    private String jsmc;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码",required = true)
    private String jsbm;

    /**
     * 是否为默认角色
     */
    @ApiModelProperty(value = "是否为默认角色",required = true)
    private Boolean sfmr;

    /**
     * 用户角色类型： in - 内部用户角色； out - 外部用户角色
     */
    @ApiModelProperty(value = "用户角色类型： in - 内部用户角色； out - 外部用户角色", required = true)
    private String yhjslx;

    /**
     * 是否删除
     */
    @ApiModelProperty("是否删除")
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long cjr;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private Long gxr;


}
