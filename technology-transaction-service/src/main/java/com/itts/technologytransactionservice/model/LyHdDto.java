package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
@Data
public class LyHdDto implements Serializable {

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
    /**
     * 活动状态(0:未开始 1:报名中 2:活动中 3:已结束)
     */
    private Integer hdzt;
    /**
     * 报名截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bmjzsj;
    /**
     * 报名开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bmkssj;
    /**
     * 活动截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date hdjssj;
    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date hdkssj;
    /*
     * 活动发布状态(0:未发布 1:已发布)
     */
    private Integer hdfbzt;
    /*
    活动内容
     */
    private String hdnr;
    /**
     *路演企业
     */
    private String lyqy;
    /**
     * 参展企业
     */
    private String czqy;
    /**
     * 展品列表
     */
    private String zplb;

}
