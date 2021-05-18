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
@TableName("t_ly_ly")
public class LyLy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 双创路演路演主键
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
     * 路演详情
     */
    private String lyxq;

    /**
     * 路演名称
     */
    private String lymc;

    /**
     * 路演图片
     */
    private String lytp;

    /**
     * 路演图片名称
     */
    private String lytpmc;

    /**
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;


}
