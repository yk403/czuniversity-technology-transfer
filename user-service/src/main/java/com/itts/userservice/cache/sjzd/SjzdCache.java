package com.itts.userservice.cache.sjzd;

import com.itts.userservice.mapper.sjzd.SjzdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description：数据字典缓存
 * @Author：lym
 * @Date: 2021/5/8
 */
@Component
public class SjzdCache {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SjzdMapper sjzdMapper;

    /**
     * 缓存数据字典模块
     */
    public void cacheSjzdModels(){

    }
}