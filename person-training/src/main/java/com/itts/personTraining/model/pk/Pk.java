package com.itts.personTraining.model.pk;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 排课表
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_pk")
public class Pk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 排课名称
     */
    private String pkmc;

    /**
     * 课程名称
     */
    private String kcmc;

    /**
     * 上课时间段
     */
    private String sksjd;

    /**
     * 星期数
     */
    private String xqs;

    /**
     * 教室名称
     */
    private String jsmc;

    /**
     * 是否下发(0:未下发;1:已下发)
     */
    private Boolean sfxf;

    /**
     * 课程编码
     */
    private String kcbm;

    /**
     * 授课老师名称
     */
    private String sklsmc;

    /**
     * 授课老师编码
     */
    private String sklsbm;

    /**
     * 删除状态(0:未删除;1已删除)
     */
    private Boolean sczt;

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
