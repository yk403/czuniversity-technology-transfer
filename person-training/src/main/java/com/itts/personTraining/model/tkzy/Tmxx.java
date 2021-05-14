package com.itts.personTraining.model.tkzy;

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
 * 题目选项
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_tmxx")
@ApiModel(value = "题目选项")
public class Tmxx implements Serializable {

    private static final long serialVersionUID = -6974805184482276883L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 题目ID
     */
    @ApiModelProperty(value = "题目ID", required = true)
    private Long tmId;

    /**
     * 选项内容
     */
    @ApiModelProperty(value = "选项内容", required = true)
    private String xxnr;

    /**
     * 是否正确答案
     */
    @ApiModelProperty(value = "是否正确答案", required = true)
    private Boolean sfzqda;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long cjr;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long gxr;
}
