package com.itts.userservice.request.rmdt;

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
 * 
 * </p>
 *
 * @author fuli
 * @since 2021-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_rmdt")
public class AddRmdtRequest implements Serializable {


    private static final long serialVersionUID = -4590335824951780286L;
    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String mc;

    /**
     * 排序
     */
    private String px;

    /**
     * 是否显示
     */
    private Boolean sfxs;

    /**
     * 开始使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date kssysj;

    /**
     * 暂停使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ztsysj;

    /**
     * 简介
     */
    private String jj;

    /**
     * 机构ID
     */
    private Long jgId;

    /**
     * 是否删除
     */
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;


}
