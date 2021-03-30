package com.itts.userservice.model.js;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author lym
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_js")
public class Js implements Serializable {

    private static final long serialVersionUID = 4981981793310936335L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String jsmc;

    /**
     * 角色编码
     */
    private String jsbm;

    /**
     * 系统类型：TECHNOLOGY_TRANSACTION_PORTAL - 技术交易门户；TALENT_TRAINING_PORTAL - 人才培养门户
     */
    private String xtlx;

    /**
     * 是否是内部角色
     */
    private Boolean sfnb;

    /**
     * 是否为默认角色
     */
    private Boolean sfmr;

    /**
     * 是否删除
     */
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;


}
