package com.itts.personTraining.vo.kssj;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/28
 */
@Data
@ApiModel(value = "考试记录题目选项")
public class GetKsjlTmXxVO implements Serializable {

    private static final long serialVersionUID = -6927642450799198556L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 考试记录ID
     */
    @ApiModelProperty(value = "考试记录ID")
    private Long ksjlId;

    /**
     * 题目ID
     */
    @ApiModelProperty(value = "题目ID")
    private Long tmId;

    /**
     * 选项编号
     */
    @ApiModelProperty(value = "选项编号")
    private String xxbh;

    /**
     * 选项内容
     */
    @ApiModelProperty(value = "选项内容")
    private String xxnr;

    /**
     * 是否选中
     */
    @ApiModelProperty(value = "是否选中")
    private Boolean sfxz;

    /**
     * 是否正确答案
     */
    @ApiModelProperty(value = "是否正确答案")
    private Boolean sfzqda;
}