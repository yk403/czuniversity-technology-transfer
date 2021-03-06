package com.itts.personTraining.model.ksExp;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

/**
 * <p>
 * 考试扩展表
 * </p>
 *
 * @author Austin
 * @since 2021-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ks_exp")
public class KsExp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 考试ID
     */
    @TableField(value = "ks_id",updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.BIGINT)
    private Long ksId;

    /**
     * 学校教室ID
     */
    @TableField(value = "xxjs_id",updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.BIGINT)
    private Long xxjsId;

    /**
     * 课程ID
     */
    private Long kcId;

    /**
     * 考试日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(value = "ksrq",updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.DATE)
    private Date ksrq;

    /**
     * 考试开始时间
     */
    @TableField(value = "kskssj",updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String kskssj;

    /**
     * 考试结束时间
     */
    @TableField(value = "ksjssj",updateStrategy = FieldStrategy.IGNORED,jdbcType = JdbcType.VARCHAR)
    private String ksjssj;

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
