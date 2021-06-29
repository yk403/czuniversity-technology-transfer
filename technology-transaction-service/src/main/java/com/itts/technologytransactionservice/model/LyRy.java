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
@EqualsAndHashCode(callSuper = false)
@TableName("t_ly_ry")
public class LyRy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 双创路演人员主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;
    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;

    /**
     * 关联专家id
     */
    private Long zjId;

    /**
     * 关联活动id(路演活动)
     */
    private Long hdId;
    /**
     * 资讯类型(0:留言咨询,1:限时咨询)
     */
    private Integer zxlx;
    /**
     * 咨询开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date zxkssj;
    /**
     * 咨询结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date zxjssj;
    /**
    * @Description: 人员简介
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/6/11
    */
    private String ryjj;
    /**
    * @Description: 联系电话
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/6/11
    */
    private String lxdh;
}
