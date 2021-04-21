package com.itts.personTraining.model.pc;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 批次表
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_pc")
public class Pc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 批次名称
     */
    private String pcmc;

    /**
     * 批次号
     */
    private String pch;

    /**
     * 批次类型:	postgraduate - 研究生;	broker - 经纪人
     */
    private String pclx;

    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private Boolean sczt;

    /**
     * 批次年份
     */
    private String pcnf;

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
