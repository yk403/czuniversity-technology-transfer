package com.itts.common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
@Data
public class UserInfo {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String name;
    private Integer type;
    private String token;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8" )
    private Date date;
    private String status;
    private boolean isMember;
    private String memberType;
    private Collection<GrantedAuthority> authorities;
    private  boolean accountNonExpired;
    private  boolean accountNonLocked;
    private  boolean credentialsNonExpired;
    private  boolean enabled;
    public UserInfo() {

    }

    public UserInfo(String userId, String name, Integer type, String token, Date date, String status, boolean isMember, String memberType, Collection<GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.token = token;
        this.date = date;
        this.status = status;
        this.isMember = isMember;
        this.memberType = memberType;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }
}

