package com.itts.personTraining.model.xsCj;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 学生成绩表
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_xs_cj")
public class XsCj implements Serializable {

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
     * 类型(1:学历学位教育;2:继续教育)
     */
    private Integer type;

    /**
     * 综合成绩
     */
    private String zhcj;

    /**
     * 总学分
     */
    private Integer zxf;

    /**
     * 论文成绩
     */
    private String lwcj;

    /**
     * 是否下发（0：否；1：是）
     */
    private Boolean sfxf;

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
