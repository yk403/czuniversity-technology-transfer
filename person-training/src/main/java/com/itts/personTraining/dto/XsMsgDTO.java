package com.itts.personTraining.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Austin
 * @Data: 2021/6/8
 * @Version: 1.0.0
 * @Description: 学生综合信息
 */
@Data
@ApiModel("学生综合信息对象")
public class XsMsgDTO {

    /**
     * 学生ID
     */
    private Long id;

    /**
     * 院系
     */
    @ApiModelProperty(value = "院系")
    private String yx;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号", required = true)
    private String xh;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = true)
    private String xm;

    /**
     * 联系电话(手机)
     */
    @ApiModelProperty(value = "联系电话(手机)", required = true)
    private String lxdh;

    /**
     * 原专业代码
     */
    @ApiModelProperty(value = "原专业代码", required = true)
    private String yzydm;

    /**
     * 原专业
     */
    @ApiModelProperty(value = "原专业", required = true)
    private String yzy;

    /**
     * 考试通知
     */
    @ApiModelProperty(value = "考试通知")
    private Long kstz;

    /**
     * 成绩通知
     */
    @ApiModelProperty(value = "成绩通知")
    private Long cjtz;

    /**
     * 实践通知
     */
    @ApiModelProperty(value = "实践通知")
    private Long sjtz;

    /**
     * 消费通知
     */
    @ApiModelProperty(value = "消费通知")
    private Long xftz;

    /**
     * 其他通知
     */
    @ApiModelProperty(value = "其他通知")
    private Long qttz;

}
