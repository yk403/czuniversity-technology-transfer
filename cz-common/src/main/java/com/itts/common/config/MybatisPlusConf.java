package com.itts.common.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.itts.common.handler.MyMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
* @Description: mybatisplus自动填充功能
* @Param: 
* @return: 
* @Author: yukai
* @Date: 2021/6/22
*/
@Configuration
public class MybatisPlusConf {
    /**
     * 自动填充功能
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
        return globalConfig;
    }

}