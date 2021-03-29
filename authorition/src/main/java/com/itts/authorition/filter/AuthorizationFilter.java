package com.itts.authorition.filter;

import cn.hutool.json.JSONUtil;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.RedisConstant;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description：用户权限校验过滤器
 * @Author：lym
 * @Date: 2021/3/24
 */
@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private RedisTemplate redisTemplate;

    public AuthorizationFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        if (authentication == null) {

            chain.doFilter(request, response);
            throw new WebException(ErrorCodeEnum.NO_PERMISSION_ERROR);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    /**
     * 获取用户信息
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(SystemConstant.TOKEN_PREFIX);

        //验证当前用户token是否有效
        Object checkToken = redisTemplate.opsForValue().get(RedisConstant.REDIS_USER_LOGIN_TOKEN_PREFIX + token);
        if (checkToken == null) {
            throw new WebException(ErrorCodeEnum.NO_LOGIN_ERROR);
        }

        try {

            //解析token
            Claims claims = JwtUtil.getTokenBody(token);
            LoginUser loginUser = JSONUtil.toBean(claims.get("data").toString(), LoginUser.class);

            return new UsernamePasswordAuthenticationToken(loginUser.getUserName(), "", null);
        } catch (Exception e) {

            log.error("【用户鉴权验证】解析token错误, token: {}", token, e);
            throw new WebException(ErrorCodeEnum.NO_PERMISSION_ERROR);
        }
    }
}