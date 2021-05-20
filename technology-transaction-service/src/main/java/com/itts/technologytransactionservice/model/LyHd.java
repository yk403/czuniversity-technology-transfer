package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("t_ly_hd")
public class LyHd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 双创路演活动主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String hdmc;

    /**
     * 活动简介
     */
    private String hdjj;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 活动图片
     */
    private String hdtp;

    /**
     * 活动图片名称
     */
    private String hdtpmc;
    /**
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;


}
