package com.itts.ittsauthentication.config;

import cn.hutool.json.JSONUtil;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.RedisConstant;
import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/31
 */
@Component("roleSecurity")
public class RoleSecurity {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean check(Authentication authentication, HttpServletRequest request) {

        System.out.println(request.getRequestURI());

        String token = request.getHeader(SystemConstant.TOKEN_PREFIX);

        //验证当前用户token是否有效
        Object checkToken = redisTemplate.opsForValue().get(RedisConstant.REDIS_USER_LOGIN_TOKEN_PREFIX + token);
        if (checkToken == null) {
            return false;
        }

        Claims claims = JwtUtil.getTokenBody(token);

        if(claims == null){
            return false;
        }

        LoginUser loginUser = JSONUtil.toBean(claims.get("data").toString(), LoginUser.class);

        System.out.println(JSONUtil.toJsonStr(loginUser));

        return true;

    }
}