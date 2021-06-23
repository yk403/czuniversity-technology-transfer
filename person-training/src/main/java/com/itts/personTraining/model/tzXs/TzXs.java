package com.itts.personTraining.model.tzXs;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 通知学生关系表
 * </p>
 *
 * @author Austin
 * @since 2021-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_tz_xs")
public class TzXs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 通知ID
     */
    private Long tzId;

    /**
     * 学生ID
     */
    private Long xsId;

    /**
     * 是否读取(0: 未读; 1: 已读)
     */
    private Boolean sfdq;


}
