package com.itts.personTraining.model.pk;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 排课表
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_pk")
public class Pk implements Serializable {

    private static final long serialVersionUID = -2972604277123643798L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 批次ID
     */
    @ApiModelProperty(value = "批次ID", required = true)
    private Long pcId;

    /**
     * 师资ID
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Long szId;

    /**
     * 教室ID
     */
    @ApiModelProperty(value = "教室ID", required = true)
    private Long xxjsId;

    /**
     * 课程时间ID
     */
    @ApiModelProperty(value = "课程时间ID", required = true)
    private Long kcsjId;

    /**
     * 类型(1:学历学位排课;2:继续教育排课)
     */
    private Integer type;

    /**
     * 上课地点
     */
    private String skdd;

    /**
     * 排课名称
     */
    @ApiModelProperty(value = "排课名称", required = true)
    private String pkmc;

    /**
     * 开学日期
     */
    @ApiModelProperty(value = "开学日期", required = true)
    private String kxrq;

    /**
     * 上课起始年月日
     */
    @ApiModelProperty(value = "上课起始年月日", required = true)
    private Date skqsnyr;

    /**
     * 上课结束年月日
     */
    @ApiModelProperty(value = "上课结束年月日", required = true)
    private Date skjsnyr;

    /**
     * 起始周
     */
    private Integer qsz;

    /**
     * 结束周
     */
    private Integer jsz;

    /**
     * 星期数
     */
    @ApiModelProperty(value = "星期数", required = true)
    private String xqs;

    /**
     * 是否下发(0:未下发;1:已下发)
     */
    @ApiModelProperty(value = "是否下发", required = true)
    private Boolean sfxf;

    /**
     * 是否删除(0:未删除;1已删除)
     */
    private Boolean sfsc;

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
