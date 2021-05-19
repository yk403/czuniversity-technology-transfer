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
@TableName("t_ly_zw")
public class LyZw implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 双创路演展位主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date cjsj;

    private Date gxsj;

    /**
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;

    /**
     * 展位简介
     */
    private String zwjj;

    /**
     * 关联会展id
     */
    private Long hzId;


}
