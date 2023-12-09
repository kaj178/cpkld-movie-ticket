package com.cpkld.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Qualifier("authService")
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
            // Disable Cross Site Request Forgery (vô hiệu hóa tính năng CSRF protection)
            .csrf(AbstractHttpConfigurer::disable)
            // Configure accessing resources
            .authorizeHttpRequests(auth -> 
                auth.requestMatchers("/css/**", "/js/**", "/public/**").permitAll() 
                    .requestMatchers("/signup/**", "/api/**").permitAll()
                    // .requestMatchers("/signup/**", "/login/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CUSTOMER")
                    .anyRequest().authenticated()
            )
            .formLogin(form -> 
                form.loginPage("/login")
                    .loginProcessingUrl("/login/auth")
                    // Handle redirect after login
                    .successHandler((req, res, auth) -> {
                        Collection<? extends GrantedAuthority> auths = auth.getAuthorities();
                        if (auths.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                            res.sendRedirect("/admin/user-control");
                        }   else {
                            res.sendRedirect("/");
                        }
                    })
                    .failureUrl("/login-error")
                    // .failureHandler((req, res, e) -> {
                    //     req.getSession().setAttribute("loginError", "Tên đăng nhập hoặc mật khẩu không đúng");
                    //     res.sendRedirect("/login");
                    // })
                    .permitAll()
            )
            .logout(logout -> 
                logout.logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login")
                    .permitAll()
            );
        return http.build();
    }

}
