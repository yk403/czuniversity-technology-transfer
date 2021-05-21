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
import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/5/18
 * @Version: 1.0.0
 * @Description: 学员DTO
 */
@Data
@ApiModel("学生对象")
public class StuDTO {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long yhId;

    /**
     * 师资ID
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Long szId;

    /**
     * 批次Id
     */
    @ApiModelProperty(value = "批次Ids", required = true)
    private List<Long> pcIds;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID", required = true)
    private Long jgId;

    /**
     * 教育类型（学历学位教育；职业教育；继续教育）
     */
    @ApiModelProperty(value = "教育类型（A学历学位教育；B职业教育；C继续教育）", required = true)
    private String jylx;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号", required = true)
    private String xh;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = true)
    private String xm;

    /**
     * 年级
     */
    @ApiModelProperty(value = "年级", required = true)
    private String nj;

    /**
     * 联系电话(手机)
     */
    @ApiModelProperty(value = "联系电话(手机)", required = true)
    private String lxdh;

    /**
     * 性别(0:男;1:女)
     */
    @ApiModelProperty(value = "性别(0:男;1:女)", required = true)
    private String xb;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "出生日期", required = true)
    private Date csrq;

    /**
     * 学生类别ID	postgraduate - 研究生;	broker - 经纪人
     */
    @ApiModelProperty(value = "学生类别ID")
    private String xslbId;

    /**
     * 学生类别名称
     */
    @ApiModelProperty(value = "学生类别名称", required = true)
    private String xslbmc;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号", required = true)
    private String sfzh;

    /**
     * 研究方向
     */
    @ApiModelProperty(value = "研究方向", required = true)
    private String yjfx;

    /**
     * 籍贯
     */
    @ApiModelProperty(value = "籍贯", required = true)
    private String jg;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族", required = true)
    private String mz;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌", required = true)
    private String zzmm;

    /**
     * 入学方式
     */
    @ApiModelProperty(value = "入学方式", required = true)
    private String rxfs;

    /**
     * 原毕业院校
     */
    @ApiModelProperty(value = "原毕业院校", required = true)
    private String ybyyx;

    /**
     * 学制
     */
    @ApiModelProperty(value = "学制", required = true)
    private String xz;

    /**
     * 导师编号
     */
    @ApiModelProperty(value = "导师编号", required = true)
    private String dsbh;

    /**
     * 导师姓名
     */
    @ApiModelProperty(value = "导师姓名", required = true)
    private String dsxm;

    /**
     * 院系
     */
    @ApiModelProperty(value = "院系", required = true)
    private String yx;

    /**
     * 学习形式
     */
    @ApiModelProperty(value = "学习形式", required = true)
    private String xxxs;

    /**
     * 毕业结论
     */
    @ApiModelProperty(value = "毕业结论", required = true)
    private String byjl;

    /**
     * 入学日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "入学日期", required = true)
    private Date rxrq;

    /**
     * 家庭地址
     */
    @ApiModelProperty(value = "家庭地址", required = true)
    private String jtdz;

    /**
     * 原专业代码
     */
    @ApiModelProperty(value = "原专业代码", required = true)
    private String yzydm;

    /**
     * 原专业
     */
    @ApiModelProperty(value = "原专业", required = true)
    private String yzy;

    /**
     * 新专业ID
     */
    @ApiModelProperty(value = "新专业ID")
    private Long zyId;

    /**
     * 新专业名称
     */
    @ApiModelProperty(value = "新专业名称")
    private String zymc;

    /**
     * 是否删除(0:未删除;1已删除)
     */
    @ApiModelProperty(value = "是否删除")
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
     * 教育形式
     */
    @ApiModelProperty(value = "教育形式")
    private String jyxs;
}
