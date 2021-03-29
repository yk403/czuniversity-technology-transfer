package com.itts.ittsauthentication.bean;

import cn.hutool.json.JSONUtil;
import com.itts.common.constant.RedisConstant;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.common.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/29
 */

@Component
public class SecurityLogoutHandler implements LogoutSuccessHandler {

    private RedisTemplate redisTemplate;

    public SecurityLogoutHandler(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;

    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String token = request.getHeader(SystemConstant.TOKEN_PREFIX);

        if(StringUtils.isNotBlank(token)){
            redisTemplate.delete(RedisConstant.REDIS_USER_LOGIN_TOKEN_PREFIX + token);
        }

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSONUtil.toJsonStr(ResponseUtil.success()));
    }
}