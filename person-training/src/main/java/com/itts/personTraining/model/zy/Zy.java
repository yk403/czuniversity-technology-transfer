package com.itts.personTraining.model.zy;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 专业表
 * </p>
 *
 * @author Austin
 * @since 2021-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_zy")
public class Zy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学院ID
     */
    @ApiModelProperty(value = "学院ID", required = true)
    private Long xyId;

    /**
     * 专业名称
     */
    @ApiModelProperty(value = "专业名称", required = true)
    private String mc;

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
