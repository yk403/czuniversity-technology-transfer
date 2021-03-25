package com.itts.authorition.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.userservice.mapper.js.TJsMapper;
import com.itts.userservice.mapper.yh.TYhMapper;
import com.itts.userservice.model.js.TJs;
import com.itts.userservice.model.yh.TYh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/22
 */
@Component
public class UserPasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TYhMapper yhMapper;

    @Autowired
    private TJsMapper jsMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        //查询用户基本信息并设置userDetails
        QueryWrapper query = new QueryWrapper();
        query.eq("yhm", userName);
        query.eq("sfsc", false);

        TYh user = yhMapper.selectOne(query);
        if (user == null) {

            throw new ServiceException(ErrorCodeEnum.USER_NOT_FIND_ERROR);
        }

        //验证码账号密码是否正确
        boolean checkPassword = BCrypt.checkpw(password, user.getMm());
        if (Objects.equals(userName, user.getYhm()) && checkPassword) {

            //查询用户角色信息并设置userDetails
            List<TJs> roles = jsMapper.findByYhId(user.getId());

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (!CollectionUtils.isEmpty(roles)) {
                roles.forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role.getJsmc()));
                });
            }

            return new UsernamePasswordAuthenticationToken(userName, password, authorities);
        }

        throw new WebException(ErrorCodeEnum.LOGIN_USERNAME_PASSWORD_ERROR);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}