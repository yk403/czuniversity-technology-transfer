package com.itts.personTraining.model.xyKc;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 学院课程关系表
 * </p>
 *
 * @author Austin
 * @since 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_xy_kc")
public class XyKc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学院ID
     */
    private Long xyId;

    /**
     * 课程ID
     */
    private Long kcId;


}
