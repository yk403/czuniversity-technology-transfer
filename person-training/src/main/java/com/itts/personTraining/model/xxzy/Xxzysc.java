package com.itts.personTraining.model.xxzy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 学习资源收藏
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_xxzysc")
public class Xxzysc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long yhId;

    /**
     * 学习资源ID
     */
    private Long xxzyId;

    /**
     * 课程ID
     */
    private Long kcId;

    /**
     * 学习资源名称
     */
    private String xxzyMc;

    /**
     * 学习资源展示图片
     */
    private String xxzyZstp;

    /**
     * 课程名称
     */
    private String kcmc;

    /**
     * 创建时间
     */
    private Date cjsj;
}
