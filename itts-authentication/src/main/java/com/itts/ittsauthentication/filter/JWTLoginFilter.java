package com.itts.ittsauthentication.filter;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itts.common.utils.common.JwtUtil;
import com.itts.ittsauthentication.bean.LoginUserInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/27
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private RedisTemplate redisTemplate;

    public JWTLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/login/");
    }

    /**
     * 校验⽤户登录的⽤户名和密码是否匹配
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginUserInfo userInfo = new ObjectMapper().readValue(request.getInputStream(), LoginUserInfo.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(userInfo.getUserName(), userInfo.getUserPassword(), new ArrayList<>());

            Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            return authentication;

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * 如果登录成功，则返回客户端Token * @param request * @param response * @param chain * @param authResult * @throws IOException * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        //⽣成Token, 并存入redis
        String token = JwtUtil.getJwtToken(authResult.getName(), 1000L * 60 * 15);
        redisTemplate.opsForValue().set("itts:user:login:token:" + token, token, 1000L * 60 * 15, TimeUnit.MILLISECONDS);

        //返回数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user", authResult.getName());
        resultMap.put("token", token);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSONUtil.toJsonStr(resultMap));
    }
}