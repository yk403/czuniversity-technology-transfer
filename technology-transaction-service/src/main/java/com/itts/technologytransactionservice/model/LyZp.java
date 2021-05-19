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
@TableName("t_ly_zp")
public class LyZp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 展品主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    private Date cjsj;

    /**
     * 更新时间
     */
    private Date gxsj;

    /**
     * 展品类别
     */
    private String zplb;

    /**
     * 展品简介
     */
    private String zpjj;

    /**
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;

    /**
     * 关联展位id
     */
    private Long zwId;


}
