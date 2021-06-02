package com.itts.personTraining.model.xsKcCj;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 学生课程成绩表
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_xs_kc_cj")
public class XsKcCj implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学生成绩ID
     */
    private Long xsCjId;

    /**
     * 课程ID
     */
    private Long kcId;

    /**
     * 课程类型(1:原专业课程;2:技术转移专业课程)
     */
    private Boolean kclx;

    /**
     * 课程编号
     */
    private String kcbh;

    /**
     * 课程名称
     */
    private String kcmc;

    /**
     * 是否必修(0:否;1:是)
     */
    private Boolean sfbx;

    /**
     * 学位课(0:否;1:是)
     */
    private Boolean xwk;

    /**
     * 成绩
     */
    private String cj;

    /**
     * 选修学期(1-6)
     */
    private String xxxq;

    /**
     * 成绩属性:(正常,旷考,免考,作弊 重修,缓考,补考, 取消资格)
     */
    private String cjsx;

    /**
     * 补考成绩
     */
    private String bkcj;

    /**
     * 是否删除(0:否;1:是)
     */
    private Boolean sfsc;

    /**
     * 备注
     */
    private String bz;

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
