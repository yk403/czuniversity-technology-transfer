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
    /*
    会展名称
     */
    private String hzmc;
    /*
会展轮播图
 */
    private String hzlbt;
    /*
会展视频
*/
    private String hzsp;
    /*
会展视频名称
*/
    private String hzspmc;
    /*
会展广告
*/
    private String hzgg;
    /*
论坛首图
*/
    private String ltst;
    /*
论坛首图名称
*/
    private String ltstmc;
    /**
    * @Description: 论坛内容
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/16
    */
    private String ltnr;
}
