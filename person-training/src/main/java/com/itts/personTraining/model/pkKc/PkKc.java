package com.itts.personTraining.model.pkKc;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 排课课程关系表
 * </p>
 *
 * @author Austin
 * @since 2021-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_pk_kc")
public class PkKc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 排课ID
     */
    private Long pkId;

    /**
     * 课程ID
     */
    private Long kcId;


}
