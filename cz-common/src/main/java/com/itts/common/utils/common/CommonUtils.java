package com.itts.common.utils.common;

import java.util.UUID;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/21
 */
public class CommonUtils {

    /**
     * 生成uuid
     */
    public static String generateUUID() {

        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}