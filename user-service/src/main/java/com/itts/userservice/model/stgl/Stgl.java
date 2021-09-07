package com.itts.userservice.model.stgl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2021-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_stgl")
public class Stgl implements Serializable {


    private static final long serialVersionUID = 1430220250483625676L;
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
     * 简介
     */
    private String jj;

    /**
     * 是否显示
     */
    private Boolean sfxs;

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
