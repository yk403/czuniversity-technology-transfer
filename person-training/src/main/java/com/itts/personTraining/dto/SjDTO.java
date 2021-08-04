package com.itts.personTraining.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/5/28
 * @Version: 1.0.0
 * @Description: 实践DTO
 */
@Data
@ApiModel("实践对象")
public class SjDTO {

    /**
     * 实践主键id
     */
    private Long id;

    /**
     * 学生ID
     */
    @ApiModelProperty(value = "学生ID", required = true)
    private Long xsId;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号")
    private String xh;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String xm;

    /**
     * 原专业
     */
    @ApiModelProperty(value = "原专业")
    private String yzy;

    /**
     * 批次ID
     */
    @ApiModelProperty(value = "批次ID")
    private Long pcId;

    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号")
    private String pch;

    /**
     * 实践名称
     */
    @ApiModelProperty(value = "实践名称", required = true)
    private String sjmc;

    /**
     * 实践类型
     */
    @ApiModelProperty(value = "实践类型", required = true)
    private String sjlx;

    /**
     * 实践单位
     */
    @ApiModelProperty(value = "实践单位", required = true)
    private String sjdw;

    /**
     * 报告
     */
    @ApiModelProperty(value = "报告")
    private String bg;

    /**
     * 报告名称
     */
    @ApiModelProperty(value = "报告名称")
    private String bgmc;

    /**
     * 评价表
     */
    @ApiModelProperty(value = "评价表")
    private String pjb;

    /**
     * 评价表名称
     */
    @ApiModelProperty(value = "评价表名称")
    private String pjbmc;

    /**
     * 实践成绩
     */
    @ApiModelProperty(value = "实践成绩", required = true)
    private String sjcj;

    /**
     * 集萃奖学金1
     */
    @ApiModelProperty(value = "集萃奖学金1")
    private String jcjxjOne;

    /**
     * 集萃奖学金2
     */
    @ApiModelProperty(value = "集萃奖学金2")
    private String jcjxjTwo;

    /**
     * 基地基金1
     */
    @ApiModelProperty(value = "基地基金1")
    private String jdjjOne;

    /**
     * 基地基金2
     */
    @ApiModelProperty(value = "基地基金2")
    private String jdjjTwo;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ksrq;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date jsrq;

    /**
     * 是否下发(0:否;1:是)
     */
    private Boolean sfxf;

    /**
     * 是否删除(0:否;1:是)
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
