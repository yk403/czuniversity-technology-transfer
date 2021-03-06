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
@TableName("t_ly_ly")
public class  LyLy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 双创路演路演主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 路演详情
     */
    private String lyxq;

    /**
     * 企业名称
     */
    private String qymc;

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
    /**
     * 活动id
     */
    private Long hdId;
    /**
     * 路演标题
     */
    private String lybt;
    /**
     * 路演简介
     */
    private String lyjj;
    /**
     * 路演简介图片
     */
    private String lyjjtp;
    /**
     * 路演简介图片名称
     */
    private String lyjjtpmc;
    /**
     * 直播地址
     */
    private String zbdz;
    /**
     * 企业类型
     */
    private Integer qylx;
    /**
     * 联系方式
     */
    private String lxfs;
    /**
     * 发布状态
     */
    private Integer fbzt;
    /**
    * @Description: 路演视频
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/6/23
    */
    private String lysp;
    /**
    * @Description: 路演视频名称
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/6/23
    */
    private String lyspmc;
    /**
     * @Description: 路演开始时间
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/23
     */
    private String lykssj;
    /**
     * @Description: 路演地址
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/6/23
     */
    private String lydz;
}
