package com.itts.userservice.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/26
 */
@Data
public class RegisterRequest implements Serializable {

    private static final long serialVersionUID = -8810055754852541015L;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 系统类型
     */
    private String systemType;

    /**
     * 手机号码
     */
    private String mobile;
}