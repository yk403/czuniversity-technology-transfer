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
     * 用户真实姓名
     */
    private String realName;

    /**
     * 用户级别
     */
    private String userLevel;

    /**
     * 用户类别：研究生；经纪人；导师；企业导师；任课教师；管理员
     */
    private String userCategory;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 用户头像
     */
    private String theme;

    /**
     * 系统类型
     */
    private String systemType;
    /***
    * @Description: 用户所属机构id()
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/9/2
    */
    private Long jgId;
    /***
    * @Description: 所属机构类型
    * @Param:
    * @return:
    * @Author: yukai
    * @Date: 2021/9/2
    */
    private String lx;

    /**
     * 用户父级机构id
     */
    private Long fjjgId;
}