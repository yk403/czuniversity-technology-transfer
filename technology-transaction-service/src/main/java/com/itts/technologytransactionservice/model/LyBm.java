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
 * @since 2021-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ly_bm")
public class LyBm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 双创路演报名表主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date cjsj;

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
    private Boolean shzt;

    /**
     * 当前登录用户id
     */
    private Long userId;


}
