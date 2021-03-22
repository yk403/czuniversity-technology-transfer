package com.itts.authorition.config;

import com.itts.common.exception.WebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/22
 */
@Component
public class UserPasswordAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        //查询数据库， 获取用户信息
        String userNameDB = "user";
        String passwordDB = "$2a$10$9x4Xix4A2sYSe4SbsprJx.FfVhDaeyt3f.Jpyk.lSa.MnBjQGBZfS";

        //验证码账号密码是否正确
        boolean checkPassword = BCrypt.checkpw(password, passwordDB);
        if (Objects.equals(userName, userNameDB) && checkPassword) {
            return new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>());
        }

        throw new WebException("用户账号或密码错误");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}