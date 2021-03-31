package com.itts.ittsauthentication.service;

import com.itts.ittsauthentication.bean.AuthoritionUser;
import com.itts.ittsauthentication.bean.UserDetailsImpl;
import com.itts.ittsauthentication.mapper.AuthoritionUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/27
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthoritionUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetailsImpl userDetailsImpl = new UserDetailsImpl();
        AuthoritionUser userInfo = userMapper.getByUserName(username);

        if (userInfo == null) {
            throw new UsernameNotFoundException(username + "⽤户不存在");

        }

        userDetailsImpl.setUserName(username);
        userDetailsImpl.setUserPassword(userInfo.getMm());

        return userDetailsImpl;

    }
}