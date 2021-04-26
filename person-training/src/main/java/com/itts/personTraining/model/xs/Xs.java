package com.itts.personTraining.model.xs;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_xs")
@ApiModel("学生对象")
public class Xs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 师资ID
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Long szId;

    /**
     * 批次Id
     */
    @ApiModelProperty(value = "批次Id", required = true)
    private Long pcId;

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
    @ApiModelProperty(value = "学生类别ID", required = true)
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
     * 毕业证号
     */
    @ApiModelProperty(value = "毕业证号", required = true)
    private String byzh;

    /**
     * 学位证号
     */
    @ApiModelProperty(value = "学位证号", required = true)
    private String xwzh;

    /**
     * 研究方向
     */
    @ApiModelProperty(value = "研究方向", required = true)
    private String yjfx;

    /**
     * 培养类别ID
     */
    @ApiModelProperty(value = "培养类别ID", required = true)
    private String pylbId;

    /**
     * 培养类别名称
     */
    @ApiModelProperty(value = "培养类别名称", required = true)
    private String pylbmc;

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
    @ApiModelProperty(value = "师资ID", required = true)
    private String zzmm;

    /**
     * 入学方式
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String rxfs;

    /**
     * 原毕业院校
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String ybyyx;

    /**
     * 原工作单位
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String ygzdw;

    /**
     * 原工作单位地址
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String ygzdwdz;

    /**
     * 现工作单位
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String xgzdw;

    /**
     * 现工作单位地址
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String xgzdwdz;

    /**
     * 现工作单位邮编
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String xgzdwyb;

    /**
     * 学制
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String xz;

    /**
     * 学习地点
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String xxdd;

    /**
     * 本科毕业证号
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String bkbyzh;

    /**
     * 本科学位证号
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String bkxwzh;

    /**
     * 论文题目
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String lwtm;

    /**
     * 论文主题词
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String lwztc;

    /**
     * 答辩日期
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Date dbrq;

    /**
     * 毕业日期
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Date byrq;

    /**
     * 授学位日期
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Date sxwrq;

    /**
     * 学位类型
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String xwlx;

    /**
     * 导师编号
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String dsbh;

    /**
     * 导师姓名
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String dsxm;

    /**
     * 院系
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String yx;

    /**
     * 教委专业代码
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String jwzydm;

    /**
     * 学习形式
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String xxxs;

    /**
     * 毕业结论
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String byjl;

    /**
     * 校长姓名
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String xzxm;

    /**
     * 办学类型
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String bxlx;

    /**
     * 培养单位码
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String pydwm;

    /**
     * 培养单位
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String pydw;

    /**
     * 招生季节
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String zsjj;

    /**
     * 入学日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "师资ID", required = true)
    private Date rxrq;

    /**
     * 学制
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String xzTwo;

    /**
     * 准考证号
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String zkzh;

    /**
     * 联系电话(手机)
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String lxdh;

    /**
     * 家庭地址
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String jtdz;

    /**
     * 备注
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String bz;

    /**
     * 原专业代码
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String yzydm;

    /**
     * 原专业
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String yzy;

    /**
     * 新专业ID
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Long zyId;

    /**
     * 新专业名称
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String zymc;

    /**
     * 是否删除(0:未删除;1已删除)
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    @ApiModelProperty(value = "师资ID", required = true)
    private Date cjsj;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Long cjr;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "师资ID", required = true)
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    private Date gxsj;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Long gxr;

    /**
     * 教育形式
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private String jyxs;

}
