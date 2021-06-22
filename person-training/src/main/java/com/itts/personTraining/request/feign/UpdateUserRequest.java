package com.itts.personTraining.request.feign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/22
 */
@Data
@ApiModel(value = "更新用户信息请求参数")
public class UpdateUserRequest implements Serializable {

    private static final long serialVersionUID = -2246520257827960015L;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "用户邮箱")
    private String yhyx;

    @ApiModelProperty(value = "用户头像")
    private String yhtx;
}