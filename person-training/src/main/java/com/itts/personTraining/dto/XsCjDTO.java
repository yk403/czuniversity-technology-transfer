package com.itts.personTraining.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/6/1
 * @Version: 1.0.0
 * @Description: 学生成绩对象
 */
@Data
@ApiModel("学生成绩对象")
public class XsCjDTO {

    /**
     * 学生成绩主键
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
     * 批次ID
     */
    @ApiModelProperty(value = "批次ID", required = true)
    private Long pcId;

    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号")
    private String pch;

    /**
     * 类型(1:学历学位教育;2:继续教育)
     */
    @ApiModelProperty(value = "类型(1:学历学位教育;2:继续教育)")
    private Integer type;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String xm;

    /**
     * 院系
     */
    @ApiModelProperty(value = "院系")
    private String yx;

    /**
     * 原专业
     */
    @ApiModelProperty(value = "原专业")
    private String yzy;

    /**
     * 综合成绩
     */
    @ApiModelProperty(value = "综合成绩")
    private String zhcj;

    /**
     * 主修专业总学分(获得)
     */
    @ApiModelProperty(value = "主修专业总学分(获得)")
    private Integer zxzyzxf;

    /**
     * 总学分(技术转移获得)
     */
    @ApiModelProperty(value = "总学分(技术转移获得)")
    private Integer zxf;

    /**
     * 技术转移课程总分
     */
    @ApiModelProperty(value = "技术转移课程总分")
    private Integer jszykczf;

    /**
     * 论文成绩
     */
    @ApiModelProperty(value = "论文成绩")
    private String lwcj;

    /**
     * 是否下发（0：否；1：是）
     */
    private Boolean sfxf;

    /**
     * 是否查看（0：否；1：是）
     */
    private Boolean sfck;

    /**
     * 是否删除（0：否；1：是）
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

    /**
     * 学生成绩扩展集合
     */
    private List<XsKcCjDTO> xsKcCjDTOList;
}
