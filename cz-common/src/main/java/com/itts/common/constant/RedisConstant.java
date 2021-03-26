package com.itts.common.constant;

/**
 * @Description：redis存储前缀通用
 * @Author：lym
 * @Date: 2021/3/25
 */
public class RedisConstant {

    /**
     * 默认过期时间(15分钟)
     */
    public static final Long EXPIRE_DATE = 1000L * 60 * 15;

    /**
     * 用户登录token存放redis前缀
     */
    public static final String REDIS_USER_LOGIN_TOKEN_PREFIX = "itts:user:login:token:";
}