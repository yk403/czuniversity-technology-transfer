package com.itts.authorition.filter;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.itts.authorition.request.LoginRequest;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.JwtUtil;
import com.itts.common.utils.ResponseUtil;
import com.itts.userservice.model.yh.TYh;
import com.itts.userservice.service.yh.TYhService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Map;

/**
 * @Description：用户登录校验过滤器
 * @Author：lym
 * @Date: 2021/3/22
 */
@Slf4j
public class AuthenticateFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private TYhService yhService;

    public AuthenticateFilter(AuthenticationManager authenticationManager, TYhService service) {
        this.authenticationManager = authenticationManager;
        this.yhService = service;
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

            log.error("【用户鉴权登录】用户登录参数不合法", e);
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }

    /**
     * 用户登录成功， 响应数据
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String userName = authResult.getPrincipal().toString();

        //通过用户名查询用户信息
        TYh yh = yhService.getByUserName(userName);

        LoginUser loginUser = new LoginUser();
        loginUser.setUserName(userName);
        loginUser.setUserId(yh.getId());
        String token = JwtUtil.getJwtToken(JSONUtil.toJsonStr(loginUser), 1000L * 60 * 3);

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("user", loginUser);
        resultMap.put("token", token);

        response.getWriter().print(JSONUtil.toJsonStr(ResponseUtil.success(resultMap)));
    }

    /**
     * 解析request封装登录参数
     */
    private LoginRequest getLoginRequest(HttpServletRequest request) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

        if (loginRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(loginRequest.getUserName()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        return loginRequest;
    }
}