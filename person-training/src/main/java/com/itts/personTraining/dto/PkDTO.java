package com.itts.personTraining.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/4/28
 * @Version: 1.0.0
 * @Description: 排课DTO
 */

@Data
@ApiModel("排课对象")
public class PkDTO {

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
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID", required = true)
    private Long kcId;

    /**
     * 师资ID
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Long szId;

    /**
     * 教室ID
     */
    @ApiModelProperty(value = "教室ID")
    private Long xxjsId;

    /**
     * 课程时间ID
     */
    @ApiModelProperty(value = "课程时间ID", required = true)
    private Long kcsjId;

    /**
     * 课程代码
     */
    @ApiModelProperty(value = "课程代码")
    private String kcdm;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String kcmc;

    /**
     * 类型(1:学历学位排课;2:继续教育排课)
     */
    @ApiModelProperty(value = "类型(1:学历学位排课;2:继续教育排课)", required = true)
    private Integer type;

    /**
     * 上课地点
     */
    private String skdd;

    /**
     * 排课名称
     */
    @ApiModelProperty(value = "排课名称")
    private String pkmc;

    /**
     * 开学日期
     */
    @ApiModelProperty(value = "开学日期", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date kxrq;

    /**
     * 上课起始年月日
     */
    @ApiModelProperty(value = "上课起始年月日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date skqsnyr;

    /**
     * 上课结束年月日
     */
    @ApiModelProperty(value = "上课结束年月日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date skjsnyr;

    /**
     * 起始周
     */
    @ApiModelProperty(value = "起始周", required = true)
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
     * 教学楼名称
     */
    @ApiModelProperty(value = "教学楼名称")
    private String jxlmc;

    /**
     * 教室编号
     */
    @ApiModelProperty(value = "教室编号")
    private String jsbh;

    /**
     * 授课老师姓名
     */
    @ApiModelProperty(value = "授课老师姓名")
    private String dsxm;

    /**
     * 授课老师编号
     */
    @ApiModelProperty(value = "授课老师编号")
    private String dsbh;

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
