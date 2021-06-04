package com.itts.userservice.model.jggl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_jggl")
public class Jggl implements Serializable {

    private static final long serialVersionUID = -3816105986737417996L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 机构名称
     */
    @ApiModelProperty(value = "父级字段code", required = true)
    private String jgmc;

    /**
     * 机构编码
     */
    @ApiModelProperty(value = "父级字段code", required = true)
    private String jgbm;

    /**
     * 机构类别code
     */
    @ApiModelProperty(value = "父级字段code", required = true)
    private String jglbbm;

    /**
     * 机构类别
     */
    @ApiModelProperty(value = "父级字段code", required = true)
    private String jglb;

    /**
     * 父机构code
     */
    private String fjbm;

    /**
     * 父机构名称
     */
    private String fjmc;

    /**
     * 菜单层级, 以“-”分隔
     */
    private String cj;

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
