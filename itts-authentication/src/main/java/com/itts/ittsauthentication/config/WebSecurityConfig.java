package com.itts.ittsauthentication.config;

import com.itts.ittsauthentication.bean.Http401AuthenticationEntryPoint;
import com.itts.ittsauthentication.bean.Http403AuthenticationEntryPoint;
import com.itts.ittsauthentication.bean.SecurityLogoutHandler;
import com.itts.ittsauthentication.filter.JWTAuthenticationFilter;
import com.itts.ittsauthentication.filter.JWTLoginFilter;
import com.itts.ittsauthentication.mapper.AuthoritionUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/27
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthoritionUserMapper authoritionUserMapper;

    /**
     * ⽩名单，不需要校验
     */
    private static final String[] AUTH_WHITELIST = {
            "/api/register/", "/websocket/**", "/swagger-resources/**", "/swagger-ui/**",
            "/v2/api-docs", "/v3/api-docs", "/webjars/**", "/admin/1/spzb/callback/","/api/uncheck/v1/**",
            "/api/v1/JsCg/page","/api/v1/JsCg/getById/**","/api/v1/JsXq/page","/api/v1/JsXq/getById/**","/api/v1/JsLy/page",
            "/api/v1/JsLy/getById","/api/v1/JsLb/page","/api/v1/JsLb/getById/**","/api/v1/File/upload","/api/v1/JsBm/page/usr",
            "/api/v1/JsHd/page","/api/v1/JsCjRc/page","/api/v1/JsLcKz/page","/api/v1/LyBm/list","/api/v1/LyHd/list",
            "/api/v1/LyHz/list","/api/v1/LyLy/list","/api/v1/LyZp/list","/api/v1/LyZw/list","/api/v1/JsXq/PageByTJsFb",
            "/api/v1/JsHd/pageFront1","/api/v1/sjzd/list/**","/admin/api/v1/sjzd/list/**","/admin/api/v1/zj/list/**","/api/v1/zj/list/**",
            "/api/v1/LyHd/list","/api/v1/LyHz/list","/api/v1/LyHd/getById/**","/api/test/get/**","/api/v1/Fjzy/page","/api/v1/Fjzy/getById/**",
            "/api/v1/LyRy/list", "/api/v1/xxzy/pay/**", "/api/v1/yh/rpc/add/","/api/v1/jjrpxjh/sign/up/","/api/v1/jjrpxjh/getJjrpxjh/","/admin/api/v1/sjzd/list/"
            ,"/api/v1/pc/getByDictionary", "/api/v1/gngl/**", "/admin/api/v1/jggl/get/by/code/", "/admin/api/v1/jggl/find/base/list/**",
            "/api/v1/**","/admin/api/v1/jggl/find/children/by/code/**", "/admin/api/v1/sjzd/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf()
                .disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                //白名单放行所有请求(需要前端不传token)
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().access("@roleSecurity.check(authentication, request)") //.authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http401AuthenticationEntryPoint())
                //权限不足结果处理
                .accessDeniedHandler(new Http403AuthenticationEntryPoint())
                .and()
                //jwt登录和返回token给前端
                .addFilter(new JWTLoginFilter(authenticationManager(), redisTemplate, authoritionUserMapper))
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), redisTemplate, authoritionUserMapper))
                .logout().logoutUrl("/api/logout/").logoutSuccessHandler(new SecurityLogoutHandler(redisTemplate)).permitAll();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}