package com.itts.authorition.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：登录参数
 * @Author：lym
 * @Date: 2021/3/24
 */
@Data
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 2101213922874837396L;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;
}