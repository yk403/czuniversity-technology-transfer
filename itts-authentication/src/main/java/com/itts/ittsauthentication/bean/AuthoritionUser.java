package com.itts.ittsauthentication.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表 - 权限认证
 * </p>
 *
 * @author lym
 * @since 2021-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_yh")
public class AuthoritionUser implements Serializable {

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
     * 用户头像
     */
    private String yhtx;

    /**
     * 真实姓名
     */
    private String zsxm;

    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 用户类别：研究生；经纪人；导师；企业导师；任课教师；管理员
     */
    private String yhlx;

    /**
     * 用户类别
     */
    private String yhlb;

    /**
     * 用户积分
     */
    private Integer yhjf;

    /**
     * 用户级别
     */
    private String yhjb;

    /**
     * 是否删除
     */
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
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
