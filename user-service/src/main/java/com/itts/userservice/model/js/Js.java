package com.itts.userservice.model.js;

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
 * 角色表
 * </p>
 *
 * @author lym
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_js")
@ApiModel("角色对象")
public class Js implements Serializable {

    private static final long serialVersionUID = 4981981793310936335L;

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
    @ApiModelProperty(value = "角色编码", required = true)
    private String jsbm;

    /**
     * 系统类型：
     * technology_transaction_portal - 技术交易门户；
     * technology_transaction_backstage_management - 技术交易后台管理；
     * talent_training_portal - 人才培养门户；
     * talent_training_backstage_management - 人才培养后台管理
     *
     * @see com.itts.common.enums.SystemTypeEnum
     */
    @ApiModelProperty(value = "系统类型: echnology_transaction_portal - 技术交易门户；\n" +
            "technology_transaction_backstage_management - 技术交易后台管理；\n" +
            "talent_training_portal - 人才培养门户；\n" +
            "talent_training_backstage_management - 人才培养后台管理", required = true)
    private String xtlx;

    /**
     * 是否为默认角色
     */
    @ApiModelProperty(value = "是否为默认角色", required = true)
    private Boolean sfmr;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date cjsj;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long cjr;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date gxsj;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long gxr;


}
