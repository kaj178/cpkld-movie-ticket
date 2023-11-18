package com.cpkld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cpkld.model.entity.User;
import com.cpkld.repository.UserRepository;

@Service("customUserDetailService")
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email!");
        }
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            // mapRoleToAuthorities(user.getRole())
            null
        );
    }
    
    // private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Collection<Role> roles) {
    //     Collection<? extends GrantedAuthority> mapRoles = roles.stream()
    //         .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
    //         .collect(Collectors.toList());
    //     return mapRoles;
    // }
}
