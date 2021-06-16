package com.itts.personTraining.config;

import com.itts.common.constant.SystemConstant;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/15
 */
@Configuration
@Slf4j
public class CommonConfig {

    /**
     * 解决feign调用，token失效问题
     */
    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {

        return template -> {

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {

                HttpServletRequest request = attributes.getRequest();
                if (request == null) {
                    return;
                }

                template.header(SystemConstant.TOKEN_PREFIX, request.getHeader(SystemConstant.TOKEN_PREFIX));
            } else {

                log.warn("requestInterceptor获取Header空指针异常");
            }
        };
    }
}