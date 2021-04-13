package com.itts.userservice.model.cd;

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
 * 菜单操作关联表
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_cd_cz_gl")
@ApiModel("菜单操作关联对象")
public class CdCzGl implements Serializable {

    private static final long serialVersionUID = -8655403136426295159L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private Long cdId;

    /**
     * 操作ID
     */
    @ApiModelProperty("操作ID")
    private Long czId;

    /**
     * 是否删除
     */
    @ApiModelProperty("是否删除")
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建
     */
    @ApiModelProperty("创建人")
    private Long cjr;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新
     */
    @ApiModelProperty("更新人")
    private Long gxr;
}
