package com.itts.personTraining.model.sj;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 实践表
 * </p>
 *
 * @author Austin
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sj")
public class Sj implements Serializable {

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
     * 学生ID
     */
    private Long xsId;

    /**
     * 实践类型
     */
    private String sjlx;

    /**
     * 实践单位
     */
    private String sjdw;

    /**
     * 报告
     */
    private String bg;

    /**
     * 报告名称
     */
    private String bgmc;

    /**
     * 评价表
     */
    private String pjb;

    /**
     * 评价表名称
     */
    private String pjbmc;

    /**
     * 实践成绩
     */
    private String sjcj;

    /**
     * 集萃奖学金1
     */
    private String jcjxjOne;

    /**
     * 集萃奖学金2
     */
    private String jcjxjTwo;

    /**
     * 基地基金1
     */
    private String jdjjOne;

    /**
     * 基地基金2
     */
    private String jdjjTwo;

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


}
