package com.itts.personTraining.dto;

import com.itts.personTraining.model.yh.GetYhVo;
import com.itts.personTraining.model.zj.Zj;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Austin
 * @Data: 2021/8/10
 * @Version: 1.0.0
 * @Description: 专家综合信息
 */
@Data
@ApiModel("专家综合信息")
public class ZjInfoDTO extends Zj implements Serializable {

    private static final long serialVersionUID = -4743020334601629548L;

    /**
     * 用户信息
     */
    @ApiModelProperty(value = "用户信息")
    private GetYhVo yhMsg;

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
