package com.itts.personTraining.model.ks;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 考试表
 * </p>
 *
 * @author Austin
 * @since 2021-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ks")
public class Ks implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 批次ID
     */
    private Long pcId;

    /**
     * 类型 (1:学历学位考试;2:继续教育考试)
     */
    private Integer type;

    /**
     * 考试试卷id
     */
    private Long kssjId;

    /**
     * 考试试卷名称
     */
    @ApiModelProperty(value = "考试试卷名称")
    private String kssjmc;

    /**
     * 考试名称
     */
    private String ksmc;

    /**
     * 考试类别
     */
    @ApiModelProperty(value = "考试类别")
    private String kslb;

    /**
     * 考试类型(统一考试;补考)
     */
    private String kslx;

    /**
     * 考试方式(开卷、闭卷、讨论、汇报、答辩、实操)
     */
    private String ksfs;

    /**
     * 考试日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ksrq;

    /**
     * 考试地点
     */
    private String ksdd;

    /**
     * 考试开始时间
     */
    private String kskssj;

    /**
     * 考试结束时间
     */
    private String ksjssj;

    /**
     * 考试时长
     */
    private String kssc;

    /**
     * 考试开始年月日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ksksnyr;

    /**
     * 考试结束年月日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ksjsnyr;

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

    /**
     * 下发时间
     */
    private Date xfsj;


}
