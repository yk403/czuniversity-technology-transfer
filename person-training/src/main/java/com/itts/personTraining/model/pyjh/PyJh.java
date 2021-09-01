package com.itts.personTraining.model.pyjh;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 培养计划表
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_py_jh")
public class PyJh implements Serializable {

    private static final long serialVersionUID = -4184012652910218503L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级机构ID
     */
    private Long fjjgId;

    /**
     * 批次ID
     */
    private Long pcId;

    /**
     * 附件资源ID
     */
    private String fjzyId;

    /**
     * 计划名称
     */
    private String jhmc;

    /**
     * 培养目标
     */
    private String pymb;

    /**
     * 学制与学习年限
     */
    private String xzyxxnx;

    /**
     * 培养方式和方法
     */
    private String pyfshff;

    /**
     * 培养方案与培养计划
     */
    private String pyfaypyjh;

    /**
     * 课程设置和学分规定
     */
    private String kcszhxfgd;

    /**
     * 实践论文
     */
    private String sjlw;

    /**
     * 培养管理
     */
    private String pygl;

    /**
     * 是否下发(0:否;1:是)
     */
    private Boolean sfxf;

    /**
     * 是否删除(0:未删除;1已删除)
     */
    private Boolean sfsc;

    /**
     * 下发时间
     */
    private Date xfsj;

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
