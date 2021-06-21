package com.itts.personTraining.request.jjrpxjh;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/1
 */
@Data
@ApiModel("经纪人培训计划报名参数")
public class SignUpJjrpxjhRequest implements Serializable {

    private static final long serialVersionUID = -993745780901179567L;

    @ApiModelProperty(value = "是否已有账号", required = true)
    private Boolean yyzh;

    @ApiModelProperty(value = "学号", required = true)
    private String xh;

    @ApiModelProperty(value = "姓名", required = true)
    private String xm;

    @ApiModelProperty(value = "身份证号", required = true)
    private String sfzh;

    @ApiModelProperty(value = "联系电话", required = true)
    private String lxdh;
}