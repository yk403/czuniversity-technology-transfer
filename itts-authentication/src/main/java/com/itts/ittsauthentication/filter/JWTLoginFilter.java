package com.itts.ittsauthentication.filter;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.RedisConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.common.JwtUtil;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.ittsauthentication.bean.AuthoritionUser;
import com.itts.ittsauthentication.bean.LoginUserInfo;
import com.itts.ittsauthentication.mapper.AuthoritionUserMapper;
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

    private AuthoritionUserMapper authoritionUserMapper;

    public JWTLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate, AuthoritionUserMapper authoritionUserMapper) {
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.authoritionUserMapper = authoritionUserMapper;
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
     * 如果登录成功，则返回客户端Token
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        //封装登录用户信息
        AuthoritionUser user = authoritionUserMapper.getByUserName(authResult.getName());

        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getId());
        loginUser.setUserName(user.getYhm());
        loginUser.setRealName(user.getZsxm());
        loginUser.setUserLevel(user.getYhjb());

        //⽣成Token, 并存入redis
        String token = JwtUtil.getJwtToken(JSONUtil.toJsonStr(loginUser), RedisConstant.TOKEN_EXPIRE_DATE);

        redisTemplate.opsForValue().set(RedisConstant.REDIS_USER_LOGIN_TOKEN_PREFIX + user.getId(), token, RedisConstant.TOKEN_EXPIRE_DATE, TimeUnit.MILLISECONDS);

        //返回数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user", authResult.getName());
        resultMap.put("token", token + "&" + user.getId());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSONUtil.toJsonStr(ResponseUtil.success(resultMap)));
    }

    /**
     * 如果登录失败，则返回错误提示信息
     *
     * @param
     * @return
     * @author liuyingming
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSONUtil.toJsonStr(ResponseUtil.error(ErrorCodeEnum.LOGIN_USERNAME_PASSWORD_ERROR)));
    }
}