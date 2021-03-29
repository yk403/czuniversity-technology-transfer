package com.itts.ittsauthentication.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/27
 */
@Data
public class LoginUserInfo implements Serializable {

    private static final long serialVersionUID = -1020598072994132836L;

    private String userName;

    private String userPassword;
}