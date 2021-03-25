package com.itts.authorition.bean;

import lombok.Data;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/25
 */
@Data
public class SecurityUserDetails {

    /**
     * 用户名
     */
    //private String userName;

    /**
     * 用户密码
     */
    //private String password;

    /**
     * 用户角色
     */
    //private List<TJs> roles;
/*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getJsmc()));
            });
        }
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }*/
}