package com.itts.ittsauthentication.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.RoleInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/3/27
 */
public class UserDetailsImpl implements UserDetails {

    private String userName;

    private String userPassword;

    private List<RoleInfo> roleInfoList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<RoleInfo> roleInfos = this.getRoleInfoList();
        if (roleInfos != null && roleInfos.size() > 0) {
            for (RoleInfo roleInfo : roleInfos) {
                authorities.add(new SimpleGrantedAuthority(roleInfo.getName()));
            }

        }

        return null;

    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
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
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<RoleInfo> getRoleInfoList() {
        return roleInfoList;
    }

    public void setRoleInfoList(List<RoleInfo> roleInfoList) {
        this.roleInfoList = roleInfoList;
    }
}