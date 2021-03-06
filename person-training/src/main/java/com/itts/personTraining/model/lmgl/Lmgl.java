package com.itts.personTraining.model.lmgl;

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
 * 
 * </p>
 *
 * @author fuli
 * @since 2021-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_lmgl")
public class Lmgl implements Serializable {


    private static final long serialVersionUID = -7473937665716615284L;
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
     * 图标
     */
    private String tbmc;
    /**
     * 图标
     */
    private String tbdz;
    /**
     * 网址
     */
    private String wz;

    /**
     * 排序
     */
    private String px;
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
    /**
     * 是否使用
     */
    private Boolean sfsy;

    /**
     * 使用起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sysj;

    /**
     * 禁用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date jysj;

    private String lmbm;


}
