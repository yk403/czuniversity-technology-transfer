package com.itts.common.constant;

import com.itts.common.bean.LoginUser;

/**
 * @ClassName SystemConstant
 * @Description
 * @Author zslme prizslme@aliyun.com
 * @Date2020/6/3 13:50
 **/
public class SystemConstant {

    /**
     * 后台管理系统URL前缀
     */
    public static final String ADMIN_BASE_URL = "/api/admin";

    /**
     * 客户端、门户URL前缀
     */
    public static final String BASE_URL = "/api";

    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "token";

    /**
     * 后台人才培养系统URL前缀
     */
    public static final String PERSONNEL_BASE_URL = "/api/personnel";
    
    /**
     *存放用户登录信息， threadLocal
     */
    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();
}
