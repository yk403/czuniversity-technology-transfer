package com.itts.personTraining.model.zj;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 专家表
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_zj")
public class Zj implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 身份证号
     */
    private String sfzh;

    /**
     * 性别
     */
    private String xb;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sr;

    /**
     * 民族
     */
    private String mz;

    /**
     * 党派
     */
    private String dp;

    /**
     * 学历
     */
    private String xl;

    /**
     * 专业技术职位
     */
    private String zyjszw;

    /**
     * 单位(大学)
     */
    private String dw;

    /**
     * 地址
     */
    private String dz;

    /**
     * 电话
     */
    private String dh;

    /**
     * 座机号
     */
    private String zjh;

    /**
     * 所属行业
     */
    private String sshy;

    /**
     * 从事学科
     */
    private String csxk;

    /**
     * 专长方向
     */
    private String zcfx;

    /**
     * 研究成果
     */
    private String yjcg;

    /**
     * 电子邮件
     */
    private String dzyj;

    /**
     * 研究领域
     */
    private String yjly;

    /**
     * 知识产权
     */
    private String zscq;

    /**
     * 在建项目
     */
    private String zjxm;

    /**
     * 论文
     */
    private String lw;

    /**
     * 专利号
     */
    private String zlh;

    /**
     * 备注
     */
    private String bz;

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


}
