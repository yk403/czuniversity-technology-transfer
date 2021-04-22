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
     * 批次ID
     */
    private Long pcId;

    /**
     * 排课名称
     */
    private String pkmc;

    /**
     * 课程名称
     */
    private String kcmc;

    /**
     * 上课起始年月
     */
    private String skqsny;

    /**
     * 上课结束年月
     */
    private String skjsny;

    /**
     * 上课时间段
     */
    private String sksjd;

    /**
     * 星期数
     */
    private String xqs;

    /**
     * 第几周
     */
    private String djz;

    /**
     * 教室ID
     */
    private Long xxjsId;

    /**
     * 教学楼名称
     */
    private String jxlmc;

    /**
     * 教室编号
     */
    private String jsbh;

    /**
     * 是否下发(0:未下发;1:已下发)
     */
    private Boolean sfxf;

    /**
     * 课程代码
     */
    private String kcdm;

    /**
     * 授课老师姓名
     */
    private String dsxm;

    /**
     * 授课老师编号
     */
    private String dsbh;

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
