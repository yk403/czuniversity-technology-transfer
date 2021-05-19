package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ly_hz")
public class LyHz implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会展主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会展介绍
     */
    private String hzjs;

    /**
     * 创建时间
     */
    private Date cjsj;

    /**
     * 更新时间
     */
    private Date gxsj;

    /**
     * 会展图片
     */
    private String hztp;

    /**
     * 会展图片名称
     */
    private String hztpmc;

    /**
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;

    /**
     * 会展关联活动id
     */
    private Long hdId;


}
