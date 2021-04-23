package com.itts.userservice.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/26
 */
@Data
@ApiModel("用户注册请求参数")
public class RegisterRequest implements Serializable {

    private static final long serialVersionUID = -8810055754852541015L;

    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String userName;

    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    private String password;

    /**
     * 用户类型：in - 内部用户； out - 外部用户
     */
    @ApiModelProperty("用户类型：in - 内部用户； out - 外部用户")
    private String userType;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String mobile;
}