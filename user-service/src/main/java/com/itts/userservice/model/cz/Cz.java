package com.itts.userservice.model.cz;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 操作表
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_cz")
public class Cz implements Serializable {

    private static final long serialVersionUID = 2063157661520960391L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作名称
     */
    private String czmc;

    /**
     * 操作编码
     */
    private String czbm;

    /**
     * 系统类型：
     * technology_transaction_portal - 技术交易门户；
     * technology_transaction_backstage_management - 技术交易后台管理；
     * talent_training_portal - 人才培养门户；
     * talent_training_backstage_management - 人才培养后台管理
     *
     * @see com.itts.common.enums.SystemTypeEnum
     */
    private String xtlx;

    /**
     * 模块类型:front - 门户; admin - 后台管理
     */
    private String mklx;

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
