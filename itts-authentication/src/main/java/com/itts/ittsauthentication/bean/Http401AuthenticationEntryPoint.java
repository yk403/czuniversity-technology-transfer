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

    private String headerValue;

    public Http401AuthenticationEntryPoint(String headerValue) {
        this.headerValue = headerValue;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        /*response.setHeader("Authorization", this.headerValue);
        //⽆权限返回401；
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());*/

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSONUtil.toJsonStr(ResponseUtil.error(ErrorCodeEnum.NO_PERMISSION_ERROR)));
    }
}