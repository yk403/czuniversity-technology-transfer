package com.itts.common.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/24
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 7436438348053091389L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户登录账号
     */
    private String userName;

    /**
     * 用户头像
     */
    private String theme;

    /**
     * 角色名称
     */
    private String roleName;
}