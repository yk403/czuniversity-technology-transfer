package com.itts.ittsauthentication.filter;

import cn.hutool.json.JSONUtil;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.RedisConstant;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
import java.util.ArrayList;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/27
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private RedisTemplate redisTemplate;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //获取Token信息
        String token = request.getHeader(SystemConstant.TOKEN_PREFIX);
        if (StringUtils.isBlank(token)) {
            chain.doFilter(request, response);
            return;
        }

        //校验Token是否正确
        UsernamePasswordAuthenticationToken authenticationToken = this.getAuthentication(request);
        //Token校验失败
        if (authenticationToken == null) {
            chain.doFilter(request, response);
            return;
        }

        //如果Token正确，设置Token到上下⽂中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * 校验Token值是否正确
     *
     * @param request
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(SystemConstant.TOKEN_PREFIX);

        //验证当前用户token是否有效
        Object checkToken = redisTemplate.opsForValue().get(RedisConstant.REDIS_USER_LOGIN_TOKEN_PREFIX + token);
        if (checkToken == null) {
            throw new WebException(ErrorCodeEnum.NO_PERMISSION_ERROR);
        }

        try {

            Claims claims = JwtUtil.getTokenBody(token);
            LoginUser loginUser = JSONUtil.toBean(claims.get("data").toString(), LoginUser.class);

            //将用户信息设置到threadLocal
            SystemConstant.threadLocal.set(loginUser);

            //如果Token值正确，则返回认证信息
            if (loginUser != null) {
                return new UsernamePasswordAuthenticationToken(loginUser.getUserName(), null, new ArrayList<>());
            }

        } catch (ExpiredJwtException e) {
            logger.error("Token已过期: {} " + e);
            throw new WebException(ErrorCodeEnum.NO_PERMISSION_ERROR);
        }

        //校验Token失败
        return null;
    }
}