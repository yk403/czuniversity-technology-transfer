package com.itts.personTraining.model.jhKc;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 计划课程关系表
 * </p>
 *
 * @author Austin
 * @since 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_jh_kc")
public class JhKc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 培养计划ID
     */
    private Long jhId;

    /**
     * 课程ID
     */
    private Long kcId;


}
