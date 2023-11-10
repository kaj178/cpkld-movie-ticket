package com.cpkld.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cpkld.model.entity.Role;
import com.cpkld.model.entity.User;

public class CustomUserDetails implements UserDetails {
    private String fullname;
    private String username;
    private String password;
    private Integer status;
    private Role role;

    public CustomUserDetails(User user) {
        this.fullname = user.getCustomer().getFullName();
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.role = user.getRole();
    }

    public String getFullname() {
        return this.fullname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> roleList = new ArrayList<>();
        roleList.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        return roleList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Integer getStatus() {
        return this.status;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == 1 ? true : false; 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == 1;
    }
}
