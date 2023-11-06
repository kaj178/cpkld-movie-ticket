package com.cpkld.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthController {

    @GetMapping("login")
    public String getLoginPage() {
        return "View/Login_Modal/LoginModal";
    }

    @GetMapping("signup")
    public String getSignupPage() {
        return "View/Signup_Modal/index";
    }
    
}
