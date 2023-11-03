package com.cpkld.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService service;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service)
            .passwordEncoder(passwordEncoder());
    }

    @Bean 
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable Cross Site Request Forgery ( vô hiệu hóa tính năng CSRF protection)
            .csrf(AbstractHttpConfigurer::disable)
            // Configure accessing resources
            .authorizeHttpRequests(auth -> 
                auth.requestMatchers("/css/**", "/js/**", "/public/**").permitAll() 
                    .requestMatchers("/", "/api/**").permitAll()
                    .requestMatchers("/signup/**", "/login/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    // .requestMatchers("/").hasRole("CUSTOMER")
                    .anyRequest().authenticated()
            )
            .formLogin(form -> 
                form.loginPage("/login")
                    .loginProcessingUrl("/login/auth")
                    // Handle redirect after login
                    .successHandler((req, res, auth) -> {
                        Collection<? extends GrantedAuthority> auths = auth.getAuthorities();
                        if (auths.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                            res.sendRedirect("/admin/user-control");
                        } else {
                            res.sendRedirect("/");
                        }
                    })
                    .permitAll()
            )
            .logout(logout -> 
                logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .permitAll()
            );
        return http.build();
    }

}
