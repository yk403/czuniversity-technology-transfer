package com.itts.authorition.handler;

import cn.hutool.json.JSONUtil;
import cn.hutool.system.UserInfo;
import com.alibaba.fastjson.JSONArray;
import com.itts.common.constant.RedisConstant;
import com.itts.common.utils.common.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description：登出处理器
 * @Author：lym
 * @Date: 2021/3/25
 */
@Component
public class SecurityLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String token = request.getHeader("token");

        if(!StringUtils.isBlank(token)){

            redisTemplate.delete(RedisConstant.REDIS_USER_LOGIN_TOKEN_PREFIX + token);
        }

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSONUtil.toJsonStr(ResponseUtil.success()));
    }
}