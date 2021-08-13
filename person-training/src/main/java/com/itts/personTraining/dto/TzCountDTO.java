package com.itts.personTraining.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Austin
 * @Data: 2021/8/13
 * @Version: 1.0.0
 * @Description: 通知数对象
 */
@Data
@ApiModel("通知数对象")
public class TzCountDTO {
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
