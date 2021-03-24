package com.itts.userservice.model.yh;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author lym
 * @since 2021-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_yh")
public class TYh implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String yhm;

    /**
     * 密码
     */
    private String mm;

    /**
     * 用户编号
     */
    private String yhbh;

    /**
     * 真实姓名
     */
    private String zsxm;

    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 用户类型
     */
    private String yhlx;

    /**
     * 用户级别
     */
    private String yhjb;

    /**
     * 是否会员
     */
    private Boolean sfhy;

    /**
     * 会员类型
     */
    private Long hylxId;

    /**
     * 是否删除
     */
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @TableField(fill= FieldFill.INSERT)
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;


}
