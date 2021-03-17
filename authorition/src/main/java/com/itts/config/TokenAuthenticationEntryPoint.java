package com.itts.config;

import com.alibaba.fastjson.JSONArray;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
*@Description:
*@Class: TokenAuthenticationEntryPoint
*@Author: zslme
*@date: 2020/3/11
*/
@Component
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        // 允许自定义请求头token(允许head跨域)
//        response.setHeader("Access-Control-Allow-Headers",
//                "ResponseType,Accept,Authorization,Cache-Control,Content-Type,DNT,If-Modified-Since,Keep-Alive,Origin,User-Agent,X-Requested-With,Token,x-access-token,ContentType");

        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        Map map = new HashMap(2);
        map.put("code",405);
        map.put("msg","用户未登录");
        response.getWriter().print(JSONArray.toJSON(map));
        response.flushBuffer();
    }
}
