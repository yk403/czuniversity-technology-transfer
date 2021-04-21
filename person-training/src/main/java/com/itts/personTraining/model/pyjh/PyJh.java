package com.itts.personTraining.model.pyjh;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
     * 学院名称
     */
    private String xymc;

    /**
     * 下发状态(0:未下发;1:已下发)
     */
    private Boolean xfzt;

    /**
     * 培养方案
     */
    private String pyfa;

    /**
     * 培养计划
     */
    private String pyjh;

    /**
     * 教学大纲
     */
    private String jxdg;

    /**
     * 删除状态(0:未删除;1已删除)
     */
    private Boolean sczt;

    /**
     * 上传时间
     */
    private Date scsj;

    /**
     * 下发时间
     */
    private Date xfsj;

    /**
     * 创建时间
     */
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;


}
