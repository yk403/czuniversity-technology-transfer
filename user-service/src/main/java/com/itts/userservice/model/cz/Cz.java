package com.itts.userservice.model.cz;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @since 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_cz")
@ApiModel("操作对象")
public class Cz implements Serializable {

    private static final long serialVersionUID = -4828901699563254750L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作名称
     */
    @ApiModelProperty(value = "操作名称", required = true)
    private String czmc;

    /**
     * 操作编码
     */
    @ApiModelProperty(value = "操作编码", required = true)
    private String czbm;

    /**
     * 是否删除
     */
    @ApiModelProperty("是否删除")
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long cjr;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private Long gxr;


}
