package com.itts.userservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/15
 */
@Configuration
@Data
public class HuaWeiLiveConfig {

    /**
     * 获取Token请求地址
     */
    @Value("${huawei.live.tokenUrl}")
    private String tokenUrl;

    /**
     * 视频处理请求地址
     */
    @Value("${huawei.live.videoUrl}")
    private String videoUrl;

    /**
     * 华为云账号
     */
    @Value("${huawei.live.account}")
    private String account;

    /**
     * 华为云登录账号
     */
    @Value("${huawei.live.username}")
    private String userName;

    /**
     * 华为云密码
     */
    @Value("${huawei.live.password}")
    private String password;

    /**
     * 华为项目ID
     */
    @Value("${huawei.live.projectId}")
    private String projectId;

    @Value("${huawei.live.templateGroupName}")
    private String templateGroupName;

    /**
     * 获取token请求地址
     */
    public static final String GET_TOKEN_URL = "/v3/auth/tokens";

    /**
     * 处理视频请求地址
     */
    public static final String DEAL_VIDEO_URL = "/v1.0/%s/asset/process";

    /**
     * 获取媒资信息请求地址
     */
    public static final String GET_ASSET_DETAIL_URL = "/v1.0/%s/asset/details";

    /**
     * token缓存key
     */
    public static final String CACHE_TOKEN_KEY = "itts:live:token";
}