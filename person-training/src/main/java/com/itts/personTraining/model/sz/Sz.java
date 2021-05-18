package com.itts.personTraining.model.sz;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 师资表
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sz")
public class Sz implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 导师编号
     */
    @ApiModelProperty(value = "导师编号", required = true)
    private String dsbh;

    /**
     * 导师姓名
     */
    private String dsxm;

    /**
     * 性别:	male - 男;	female - 女
     */
    private String xb;

    /**
     * 身份证号
     */
    private String sfzh;

    /**
     * 籍贯
     */
    private String jg;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date csrq;

    /**
     * 民族
     */
    private String mz;

    /**
     * 政治面貌
     */
    private String zzmm;

    /**
     * 文化程度
     */
    private String whcd;

    /**
     * 院系
     */
    private String yx;

    /**
     * 干部职务
     */
    private String gbzw;

    /**
     * 从事专业1
     */
    private String cszyOne;

    /**
     * 从事专业2
     */
    private String cszyTwo;

    /**
     * 从事专业3
     */
    private String cszyThree;

    /**
     * 技术职称
     */
    private String jszc;

    /**
     * 单位电话
     */
    private String dwdh;

    /**
     * 批准硕导年月
     */
    private String pzsdny;

    /**
     * 批准博导年月
     */
    private String pzbdny;

    /**
     * 在岗状态
     */
    private String zgzt;

    /**
     * 导师类别:	tutor - 研究生导师;	corporate_mentor - 企业导师;	teacher - 授课教师
     */
    private String dslb;

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

    /**
     * 个人照片
     */
    private String grzp;

    /**
     * 行业领域
     */
    private String hyly;

    /**
     * 所属机构
     */
    private String ssjg;

    /**
     * 专属资格证
     */
    private String zszgz;

    /**
     * 驻入时间
     */
    private Date zrsj;

}
