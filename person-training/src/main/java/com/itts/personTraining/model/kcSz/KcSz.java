package com.itts.personTraining.model.kcSz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程师资关系表
 * </p>
 *
 * @author Austin
 * @since 2021-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_kc_sz")
public class KcSz implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID", required = true)
    private Long kcId;

    /**
     * 师资ID
     */
    @ApiModelProperty(value = "师资ID", required = true)
    private Long szId;


}
