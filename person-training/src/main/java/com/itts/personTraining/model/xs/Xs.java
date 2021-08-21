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
public class Xs implements Serializable {

    private static final long serialVersionUID = -8679063028284005847L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long yhId;

    /**
     * 企业导师ID
     */
    private Long qydsId;

    /**
     * 原专业导师ID
     */
    private Long yzydsId;

    /**
     * 机构ID
     */
    private Long jgId;

    /**
     * 院系
     */
    private String yx;

    /**
     * 学号
     */
    private String xh;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 年级
     */
    private String nj;

    /**
     * 性别(0:男;1:女)
     */
    private String xb;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "出生日期", required = true)
    private Date csrq;



    /**
     * 学生类别名称 postgraduate - 研究生;	broker - 经纪人
     */
    private String xslbmc;

    /**
     * 身份证号
     */
    private String sfzh;

    /**
     * 研究方向
     */
    private String yjfx;

    /**
     * 籍贯
     */
    private String jg;

    /**
     * 民族
     */
    private String mz;

    /**
     * 政治面貌
     */
    private String zzmm;

    /**
     * 入学方式
     */
    private String rxfs;

    /**
     * 原毕业院校
     */
    private String ybyyx;

    /**
     * 学制
     */
    private String xz;

    /**
     * 学习形式
     */
    private String xxxs;

    /**
     * 毕业结论
     */
    private String byjl;

    /**
     * 入学日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rxrq;

    /**
     * 联系电话(手机)
     */
    private String lxdh;

    /**
     * 家庭地址
     */
    private String jtdz;

    /**
     * 原专业代码
     */
    private String yzydm;

    /**
     * 原专业
     */
    private String yzy;

    /**
     * 新专业ID
     */
    private Long zyId;

    /**
     * 新专业名称
     */
    private String zymc;

    /**
     * 报名方式
     */
    private String bmfs;

    /**
     * 是否删除(0:未删除;1已删除)
     */
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;

    /**
     * 教育形式
     */
    private String jyxs;

}
