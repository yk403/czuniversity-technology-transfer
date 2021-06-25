package com.itts.personTraining.model.tzSz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 通知师资表
 * </p>
 *
 * @author Austin
 * @since 2021-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_tz_sz")
public class TzSz implements Serializable {

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
     * 师资ID
     */
    private Long szId;

    /**
     * 是否读取
     */
    private Boolean sfdq;


}
