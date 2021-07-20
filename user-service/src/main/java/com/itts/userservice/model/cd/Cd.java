package com.itts.userservice.model.cd;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_cd")
@ApiModel("菜单对象")
public class Cd implements Serializable {

    private static final long serialVersionUID = 7412803256182919847L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称",required = true)
    private String cdmc;

    /**
     * 菜单编码
     */
    @ApiModelProperty(value = "菜单编码", required = true)
    private String cdbm;

    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String cdtb;

    /**
     * 父级菜单ID
     */
    @ApiModelProperty(value = "父级菜单ID", required = true)
    private Long fjcdId;

    /**
     * 菜单层级, 以“-”分隔
     */
    @ApiModelProperty("菜单层级, 以“-”分隔")
    private String cj;

    /**
     * 组件地址
     */
    @ApiModelProperty("组件地址")
    private String zjdz;

    /**
     * 菜单地址
     */
    @ApiModelProperty("菜单地址")
    private String cddz;

    /**
     * 系统类型：
     * technology_transaction_portal - 技术交易门户；
     * technology_transaction_backstage_management - 技术交易后台管理；
     * talent_training_portal - 人才培养门户；
     * talent_training_backstage_management - 人才培养后台管理
     *
     * @see com.itts.common.enums.SystemTypeEnum
     */
    @ApiModelProperty("系统类型：\n" +
            "technology_transaction_portal - 技术交易门户；\n" +
            "technology_transaction_backstage_management - 技术交易后台管理；\n" +
            "talent_training_portal - 人才培养门户；\n" +
            "talent_training_backstage_management - 人才培养后台管理")
    private String xtlx;

    /**
     * 模块类型：front - 门户; admin - 后台管理
     */
    @ApiModelProperty("模块类型：front - 门户; admin - 后台管理")
    private String mklx;

    /**
     * 是否删除
     */
    @ApiModelProperty("是否删除")
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date cjsj;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long cjr;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("更新时间")
    private Date gxsj;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private Long gxr;


}
