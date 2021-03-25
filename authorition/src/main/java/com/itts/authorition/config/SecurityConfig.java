package com.itts.authorition.config;

import com.itts.authorition.filter.AuthenticateFilter;
import com.itts.authorition.filter.AuthorizationFilter;
import com.itts.authorition.service.yh.AuthoritionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/19
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthoritionUserService tYhService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //权限设置管理
                .authorizeRequests()
                //校验用户是否有USER的角色，只有有的才可以访问
                //.antMatchers(HttpMethod.GET, "/api/admin/test/authorition/**").hasRole("USER")
                //设置白名单
                .antMatchers("/api/login/", "/api/logout/", "/api/register/").permitAll()
                //所有请求都需要授权（除了放行的）
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .addFilter(new AuthenticateFilter(authenticationManager(), tYhService))
                .addFilter(new AuthorizationFilter(authenticationManager()))
                //关闭session， 不再使用
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //设置登出
                .and().logout().permitAll();
    }

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}