package com.itts.personTraining.model.ks;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
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
     * 学校教室ID
     */
    private Long xxjsId;

    /**
     * 课程ID
     */
    private Long kcId;

    /**
     * 考试名称
     */
    private String ksmc;

    /**
     * 考试开始时间
     */
    private Date kskssj;

    /**
     * 考试结束时间
     */
    private Date ksjssj;

    /**
     * 考试类型(0:统一考试;1:实训考试;2:证书考试;3:补考)
     */
    private Boolean kslx;

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
