package com.itts.userservice.model.pc;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 批次表
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_pc")
public class Pc implements Serializable {

    private static final long serialVersionUID = 723841754888857561L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 入学日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "入学日期", required = true)
    private Date rxrq;

    /**
     * 批次名称
     */
    @ApiModelProperty(value = "批次名称", required = true)
    private String pcmc;

    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号", required = true)
    private String pch;

    /**
     * 教育类型
     */
    @ApiModelProperty(value = "教育类型", required = true)
    private String jylx;

    /**
     * 学员类型
     */
    @ApiModelProperty(value = "学员类型", required = true)
    private String xylx;

    /**
     * 是否删除(0:未删除;1已删除)
     */
    private Boolean sfsc;

    /**
     * 批次年份
     */
    @ApiModelProperty(value = "批次年份", required = true)
    private String pcnf;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;

}
