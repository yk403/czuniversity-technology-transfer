package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/*
 *@Auther: yukai
 *@Date: 2021/06/03/11:10
 *@Desription:
 */
@Data
public class LyBmDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 双创路演报名表主键
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

    private String dwmc;

    private String fr;

    private String dz;

    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 报名理由
     */
    private String bmly;

    /**
     * 营业执照
     */
    private String yyzz;

    /**
     * 法人身份证
     */
    private String frsfz;

    /**
     * 联系人
     */
    private String lxr;

    /**
     * 关联活动id
     */
    private Long hdId;

    /**
     * 删除状态(0未删除;1已删除)
     */
    private Boolean isDelete;

    /**
     * 审核状态（0默认、1拒绝、2通过）
     */
    private Integer shzt;

    /**
     * 当前登录用户id
     */
    private Long userId;
    /**
     * 活动名称
     */
    private String hdmc;


}
