package com.itts.personTraining.model.tzZj;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 通知专家表
 * </p>
 *
 * @author Austin
 * @since 2021-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_tz_zj")
public class TzZj implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 通知ID
     */
    private Long tzId;

    /**
     * 专家ID
     */
    private Long zjId;

    /**
     * 是否读取(0: 未读; 1: 已读)
     */
    private Boolean sfdq;


}
