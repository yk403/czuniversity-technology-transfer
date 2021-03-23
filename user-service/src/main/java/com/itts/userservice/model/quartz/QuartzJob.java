package com.itts.userservice.model.quartz;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author zs
 * @since 2020-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuartzJob implements Serializable {
    public static final String JOB_KEY = "JOB_KEY";
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Spring Bean名称
     */
    private String beanName;

    /**
     * cron 表达式
     */
    private String cronExpression;

    /**
     * 状态：1暂停、0启用
     */
    private Boolean isPause;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建日期
     */
    private Date createTime;


}
