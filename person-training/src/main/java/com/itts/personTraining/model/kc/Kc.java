package com.itts.personTraining.model.kc;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程表
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_kc")
public class Kc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 教师ID
     */
    private Long jsId;

    /**
     * 类别:	compulsory - 必修课;	internship_training - 实习实训
     */
    private String lb;

    /**
     * 知识模块ID
     */
    private Long zsmkId;

    /**
     * 教师姓名
     */
    private String jsxm;

    /**
     * 知识模块名称
     */
    private String zsmkmc;

    /**
     * 课程名称
     */
    private String kcmc;

    /**
     * 课程代码
     */
    private String kcdm;

    /**
     * 课程类型:	original_major - 原专业课程;	technology_transfer - 技术转移专业;	broker - 经纪人课程
     */
    private String kclx;

    /**
     * 课程学时(如总学时为36学时)
     */
    private String kcxs;

    /**
     * 课程学分
     */
    private Integer kcxf;

    /**
     * 开课学期
     */
    private String kkxq;

    /**
     * 开课学院
     */
    private String kkxy;

    /**
     * 授课方式:	seminar - 讲授/研讨;	discuss - 讲授讨论;	practice - 实训
     */
    private String skfs;

    /**
     * 教学讲义
     */
    private String jxjy;

    /**
     * 教材附件
     */
    private String jcfj;

    /**
     * 课件
     */
    private String kj;

    /**
     * 讲案
     */
    private String ja;

    /**
     * 课件视频
     */
    private String kjsp;

    /**
     * 视频
     */
    private String sp;

    /**
     * 是否必修(0:否;1:是)
     */
    private Boolean sfbx;

    /**
     * 备注
     */
    private String bz;

    /**
     * 下发状态(0:未下发;1:已下发)
     */
    private Boolean xfzt;

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
