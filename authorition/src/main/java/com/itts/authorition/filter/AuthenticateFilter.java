package com.itts.authorition.filter;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itts.authorition.model.yh.AuthoritionUser;
import com.itts.authorition.request.LoginRequest;
import com.itts.authorition.service.yh.AuthoritionUserService;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.RedisConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.JwtUtil;
import com.itts.common.utils.common.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description：用户登录校验过滤器
 * @Author：lym
 * @Date: 2021/3/22
 */
@Slf4j
public class AuthenticateFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private AuthoritionUserService userService;

    private RedisTemplate redisTemplate;

    public AuthenticateFilter(AuthenticationManager authenticationManager, AuthoritionUserService userService, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        //登录请求地址
        setFilterProcessesUrl("/api/login/");
    }

    /**
     * 获取用户登录参数
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequest loginRequest = getLoginRequest(request);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
            Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            return authenticate;

        } catch (Exception e) {

            log.error("【用户鉴权登录】用户账号或密码不正确", e);
            throw new WebException(ErrorCodeEnum.LOGIN_USERNAME_PASSWORD_ERROR);
        }
    }

    /**
     * 用户登录成功， 响应数据
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String userName = authResult.getPrincipal().toString();

        //通过用户名查询用户信息
        AuthoritionUser user = userService.getByUserName(userName);

        //设置登录用户信息
        LoginUser loginUser = new LoginUser();
        loginUser.setUserName(userName);
        loginUser.setUserId(user.getId());
        loginUser.setRealName(user.getZsxm());
        loginUser.setUserLevel(user.getYhjb());

        //生成token
        String token = JwtUtil.getJwtToken(JSONUtil.toJsonStr(loginUser));
        redisTemplate.opsForValue().set(RedisConstant.REDIS_USER_LOGIN_TOKEN_PREFIX + token, token,
                RedisConstant.EXPIRE_DATE, TimeUnit.MILLISECONDS);

        //返回数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user", loginUser);
        resultMap.put("token", token);

        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(JSONUtil.toJsonStr(ResponseUtil.success(resultMap)));
    }

    /**
     * 解析request封装登录参数
     */
    private LoginRequest getLoginRequest(HttpServletRequest request) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

        if (loginRequest == null) {
            throw new WebException(ErrorCodeEnum.LOGIN_USERNAME_PASSWORD_ERROR);
        }

        if (StringUtils.isBlank(loginRequest.getUserName()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new WebException(ErrorCodeEnum.LOGIN_USERNAME_PASSWORD_ERROR);
        }

        return loginRequest;
    }
}