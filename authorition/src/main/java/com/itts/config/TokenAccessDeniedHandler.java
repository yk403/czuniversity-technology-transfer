package com.itts.config;

import com.alibaba.fastjson.JSONArray;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Create zslme
 * Date 2019/3/15 20:16
 */

@Component
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {


//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        // 允许自定义请求头token(允许head跨域)
//        httpServletResponse.setHeader("Access-Control-Allow-Headers",
//                "ResponseType,Accept,Authorization,Cache-Control,Content-Type,DNT,If-Modified-Since,Keep-Alive,Origin,User-Agent,X-Requested-With,Token,x-access-token,ContentType");

        httpServletResponse.setStatus(200);
        httpServletResponse.setContentType("application/json;charset=utf-8");

        Map map = new HashMap();
        map.put("code", 403);
        map.put("msg","权限不足");

        httpServletResponse.getWriter().print(JSONArray.toJSON(map));
        httpServletResponse.flushBuffer();
    }
}
