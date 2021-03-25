package com.itts.authorition.handler;

import cn.hutool.json.JSONUtil;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.common.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 用户权限不足自定义返回
 * @Author:
 * @date: 2020/3/11
 */
@Component
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().print(JSONUtil.toJsonStr(ResponseUtil.error(ErrorCodeEnum.NO_PERMISSION_ERROR)));
    }
}
