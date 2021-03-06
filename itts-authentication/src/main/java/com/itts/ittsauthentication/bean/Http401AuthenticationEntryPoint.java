package com.itts.ittsauthentication.bean;

import cn.hutool.json.JSONUtil;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.common.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/27
 */
public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {

    public Http401AuthenticationEntryPoint() {

    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSONUtil.toJsonStr(ResponseUtil.error(ErrorCodeEnum.NO_LOGIN_ERROR)));
    }
}