package com.itts.personTraining.model.zj;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
     * 机构id
     */
    @ApiModelProperty(value = "机构id", required = true)
    private Long jgId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long yhId;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String bh;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = true)
    private String xm;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String sfzh;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", required = true)
    private String xb;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sr;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族", required = true)
    private String mz;

    /**
     * 政治面貌(数据字典获取)
     */
    @ApiModelProperty(value = "政治面貌(数据字典获取)")
    private String zzmm;

    /**
     * 学历
     */
    @ApiModelProperty(value = "学历", required = true)
    private String xl;

    /**
     * 类型(校内; 校外)
     */
    @ApiModelProperty(value = "类型(校内;校外)", required = true)
    private String lx;

    /**
     * 专业技术职位
     */
    @ApiModelProperty(value = "专业技术职位")
    private String zyjszw;

    /**
     * 单位(大学)
     */
    @ApiModelProperty(value = "单位(大学)")
    private String dw;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String dz;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", required = true)
    private String dh;

    /**
     * 座机号
     */
    @ApiModelProperty(value = "座机号")
    private String zjh;

    /**
     * 所属行业
     */
    @ApiModelProperty(value = "所属行业")
    private String sshy;

    /**
     * 从事学科
     */
    @ApiModelProperty(value = "从事学科")
    private String csxk;

    /**
     * 专长方向
     */
    @ApiModelProperty(value = "专长方向")
    private String zcfx;

    /**
     * 研究成果
     */
    @ApiModelProperty(value = "研究成果")
    private String yjcg;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String dzyj;

    /**
     * 研究领域
     */
    @ApiModelProperty(value = "研究领域", required = true)
    private String yjly;

    /**
     * 知识产权
     */
    @ApiModelProperty(value = "知识产权")
    private String zscq;

    /**
     * 在建项目
     */
    @ApiModelProperty(value = "在建项目")
    private String zjxm;

    /**
     * 论文
     */
    @ApiModelProperty(value = "论文")
    private String lw;

    /**
     * 专利号
     */
    @ApiModelProperty(value = "专利号", required = true)
    private String zlh;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
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
