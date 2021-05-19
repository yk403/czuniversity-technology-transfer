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
            "/v2/api-docs", "/v3/api-docs", "/webjars/**", "/api/admin/v1/spzb/callback/","/api/uncheck/v1/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf()
                .disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().access("@roleSecurity.check(authentication, request)") //.authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http401AuthenticationEntryPoint())
                //权限不足结果处理
                .accessDeniedHandler(new Http403AuthenticationEntryPoint())
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager(), redisTemplate, authoritionUserMapper))
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), redisTemplate))
                .logout().logoutUrl("/api/logout/").logoutSuccessHandler(new SecurityLogoutHandler(redisTemplate)).permitAll();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}