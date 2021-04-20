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
     * token默认过去时间
     */
    public static final Long TOKEN_EXPIRE_DATE = 1000L * 60 * 60 * 24 * 7;

    /**
     * 用户菜单缓存过期时间(3天)
     */
    public static final Long USER_MENU_EXPIRE_DATE = 3L;

    /**
     * 用户登录token存放redis前缀
     */
    public static final String REDIS_USER_LOGIN_TOKEN_PREFIX = "itts:user:login:token:";

    /**
     * 用户目录前缀
     */
    public static final String USERSERVICE_MENUS = "itts:userservice:menus:";

    /**
     * 用户目录菜单操作
     */
    public static final String USERSERVICE_MENUS_OPERTION = "itts:userservice:menus:operation:";

    /**
     * 拍卖大厅存放redis前缀
     */
    public static final String BID_ACTIVITY_PREFIX = "itts:bis:websocket:";
}