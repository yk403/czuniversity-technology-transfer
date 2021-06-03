package com.itts.personTraining.model.fjzy;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 附件资源表
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_fjzy")
@ApiModel(value = "附件资源")
public class Fjzy implements Serializable {

    private static final long serialVersionUID = -5047340813320574593L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 附件资源ID
     */
    @ApiModelProperty(value = "附件资源ID", required = true)
    private String fjzyId;

    /**
     * 附件资源名称
     */
    @ApiModelProperty(value = "附件资源名称")
    private String fjzymc;

    /**
     * 附件资源地址
     */
    @ApiModelProperty(value = "附件资源地址", required = true)
    private String fjzydz;

    /**
     * 附件资源文件类型
     */
    @ApiModelProperty(value = "附件资源文件类型")
    private String fjzylx;

    /**
     * 附件资源大小(byte)
     */
    @ApiModelProperty(value = "附件资源大小(byte)")
    private Long fjzydx;

    /**
     * 附件资源时长（s）
     */
    @ApiModelProperty(value = "附件资源时长（s）")
    private Long fjzysc;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long cjr;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    private Date cjsj;
}
