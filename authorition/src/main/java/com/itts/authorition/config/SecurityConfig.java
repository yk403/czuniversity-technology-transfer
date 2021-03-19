package com.itts.authorition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/19
 */
@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Configuration
    public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    //权限设置管理
                    .authorizeRequests()
                    //校验用户是否有USER的角色，只有有的才可以访问
                    .antMatchers(HttpMethod.GET, "/api/admin/test/authorition/**").hasRole("USER")
                    //所有请求都需要授权（除了放行的）
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic();
        }
    }

    @Bean
    public UserDetailsService userDetailsService() {

        InMemoryUserDetailsManager memoryUserDetailsManager = new InMemoryUserDetailsManager();

        memoryUserDetailsManager.createUser(User
                .withUsername("user")
                .password(encoder().encode("123456"))
                .roles("USER")
                .build());

        memoryUserDetailsManager.createUser(User
                .withUsername("guest")
                .password(encoder().encode("123456"))
                .roles("GUEST")
                .build());

        return memoryUserDetailsManager;
    }

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}